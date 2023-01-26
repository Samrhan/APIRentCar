package com.bader.domain.payment;

import com.bader.domain.payment.model.CreditCard;

public interface PaymentService {
    boolean attemptPayment(CreditCard creditCard, Integer amountInCents);
}