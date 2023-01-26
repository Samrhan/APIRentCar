package com.bader.domain.payment.ports;

import com.bader.domain.payment.model.CreditCard;

public interface PaymentProvider {
    boolean attemptPayment(CreditCard creditCard, Integer amountInCents);
}
