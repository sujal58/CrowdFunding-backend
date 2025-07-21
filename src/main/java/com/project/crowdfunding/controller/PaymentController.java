package com.project.crowdfunding.controller;


import com.project.crowdfunding.Services.payment.StripePaymentService;
import com.project.crowdfunding.dto.request.PaymentIntentRequest;
import com.project.crowdfunding.dto.response.ApiResponse;
import com.project.crowdfunding.dto.response.PaymentIntentResponse;
import com.project.crowdfunding.dto.response.PaymentVerificationResponse;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final StripePaymentService paymentService;
    private final String webhookSecret;

    public PaymentController(StripePaymentService paymentService, @Value("${spring.stripe.webhookSecret}") String webhookSecret) {
        this.paymentService = paymentService;
        this.webhookSecret = webhookSecret;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody @Valid PaymentIntentRequest dto, HttpServletRequest request) {
        try {
            String clientSecret = paymentService.createPaymentIntent(dto);
            return ResponseEntity.ok().body(ApiResponse.success("Payment Initiated successfully", new PaymentIntentResponse(clientSecret), request.getRequestURI()));
        } catch (Exception e) {
            logger.error("Error creating PaymentIntent for user {}: {}", dto.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating PaymentIntent: " + e.getMessage());
        }
    }

    @GetMapping("/verify/{paymentIntentId}")
    public ResponseEntity<?> verifyPayment(@PathVariable String paymentIntentId, HttpServletRequest request) {
        try {
            PaymentVerificationResponse response = paymentService.verifyAndFinalizeDonation(paymentIntentId);
            return ResponseEntity.ok(ApiResponse.success("Payment verified successfully!", response, request.getRequestURI()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }


    @PostMapping("/webhook/stripe")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        logger.info("webhook is called");
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                if (paymentIntent != null) {
                    paymentService.verifyPayment(paymentIntent.getId(), "succeeded");
                    return ResponseEntity.ok("Webhook processed");
                }
            }
            return ResponseEntity.ok("Webhook event ignored");
        } catch (SignatureVerificationException e) {
            logger.error("Webhook signature verification failed: {}", e.getMessage(), e);
            return ResponseEntity.status(400).body("Invalid webhook signature");
        } catch (Exception e) {
            logger.error("Error processing webhook: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Webhook error: " + e.getMessage());
        }
    }
}


