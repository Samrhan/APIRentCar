package com.ssa.domain.payment.ports;

import com.ssa.domain.payment.model.CreditCard;

public interface PaymentProvider {
    boolean attemptPayment(CreditCard creditCard, Integer amountInCents);
}
