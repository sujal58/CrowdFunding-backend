package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(String orderId);
    Optional<Payment> findByPaymentId(String paymentId);
}