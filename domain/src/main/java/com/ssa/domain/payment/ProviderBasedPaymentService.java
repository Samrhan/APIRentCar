package com.ssa.domain.payment;

import com.ssa.domain.payment.model.CreditCard;
import com.ssa.domain.payment.ports.PaymentProvider;

public class ProviderBasedPaymentService implements PaymentService {
    private final PaymentProvider paymentProvider;

    public ProviderBasedPaymentService(PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public boolean attemptPayment(CreditCard creditCard, Integer amountInCents) {
        return paymentProvider.attemptPayment(creditCard, amountInCents);
    }
}
