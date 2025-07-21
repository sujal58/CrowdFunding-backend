package com.project.crowdfunding.Services.payment;

import com.project.crowdfunding.Entity.Donation;
import com.project.crowdfunding.Entity.Payment;
import com.project.crowdfunding.Repository.PaymentRepository;
import com.project.crowdfunding.Services.DonationService;
import com.project.crowdfunding.dto.request.PaymentIntentRequest;
import com.project.crowdfunding.dto.response.PaymentVerificationResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class StripePaymentService {
    private final PaymentRepository paymentRepository;

    private final DonationService donationService;

    private final String currency;




    public StripePaymentService(
            @Value("${spring.stripe.apiKey}") String apiKey,
            @Value("${spring.stripe.currency}") String currency,
            PaymentRepository paymentRepository,
            DonationService donationService

    ) {
        Stripe.apiKey = apiKey;
        this.currency = currency;
        this.paymentRepository = paymentRepository;
        this.donationService = donationService;

    }

    public String createPaymentIntent(PaymentIntentRequest dto) throws StripeException {
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setAmount((dto.getAmount().multiply(BigDecimal.valueOf(100)).longValue())) // Convert to cents/paise
                            .setCurrency(currency)
                            .setReceiptEmail(dto.getEmail())
                            .setDescription("Pledge for Campaign " + dto.getCampaignName())
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .setReceiptEmail(dto.getEmail())
                            .build()
            );

            Payment payment = new Payment();
            payment.setPaymentId(paymentIntent.getId()); // Store PaymentIntent ID
            payment.setUserEmail(dto.getEmail());
            payment.setCampaignId(dto.getCampaignId());
            payment.setAmount(dto.getAmount());
            payment.setCurrency(currency);
            payment.setStatus("created");
            paymentRepository.save(payment);

//            String username = authHelper.getAuthenticatedUsername();
//            NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
//            notificationRequestDto.setUsername(username);
//            notificationRequestDto.setNotificationType();
//
//            notificationService.sendNotification(userEmail, "Payment initiated for campaign " + campaignId);
            log.info("Created Stripe PaymentIntent for user {}: paymentIntentId={}, amount={}", dto.getEmail(), paymentIntent.getId(), dto.getAmount());
            return paymentIntent.getClientSecret();
        } catch (StripeException e) {
            log.error("Error creating PaymentIntent for user {}: {}", dto.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

    public void verifyPayment(String paymentIntentId, String status) {
        try {
            Payment payment = paymentRepository.findByOrderId(paymentIntentId);
            if (payment != null) {
                payment.setPaymentId(paymentIntentId);
                payment.setStatus(status);
                paymentRepository.save(payment);
//                notificationService.sendNotification(payment.getUserEmail(), "Payment " + status + " for campaign " + payment.getCampaignId());
                log.info("Payment verified for paymentIntentId={}: status={}", paymentIntentId, status);
            } else {
                log.warn("Payment not found for paymentIntentId={}", paymentIntentId);
            }
        } catch (Exception e) {
            log.error("Error verifying payment for paymentIntentId={}: {}", paymentIntentId, e.getMessage(), e);
        }
    }

    public String getPaymentIntentStatus(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent.getStatus(); // returns "succeeded", "requires_payment_method", etc.
        } catch (Exception e) {
            throw new RuntimeException("Stripe verification failed: " + e.getMessage());
        }
    }

    public PaymentVerificationResponse verifyAndFinalizeDonation(String paymentIntentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentIntentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        System.out.println("fetched payment: "+payment.getPaymentId());

        // Double-check payment status from Stripe
        String currentStatus = getPaymentIntentStatus(paymentIntentId);
        System.out.println("current status: "+ currentStatus);
        if (!"succeeded".equals(currentStatus)) {
            throw new IllegalArgumentException("Payment not successful. Current status: " + currentStatus);
        }

        // Only create donation once
        if (payment.getDonationId() != null) {
            return new PaymentVerificationResponse(true, payment.getDonationId(), "Already processed.");
        }

        // Create donation now
        Donation savedDonation = donationService.createFromPayment(payment);
        payment.setDonationId(savedDonation.getDonationId());// update payment with link
        paymentRepository.save(payment);

        return new PaymentVerificationResponse(true, savedDonation.getDonationId(), "Donation successful.");
    }
}
