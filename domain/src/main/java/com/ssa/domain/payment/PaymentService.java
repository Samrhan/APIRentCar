package com.ssa.domain.payment;

import com.ssa.domain.payment.model.CreditCard;

public interface PaymentService {
    boolean attemptPayment(CreditCard creditCard, Integer amountInCents);
}