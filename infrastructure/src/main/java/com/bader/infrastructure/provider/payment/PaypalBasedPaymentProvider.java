package com.bader.infrastructure.provider.payment;

import com.bader.domain.payment.model.CreditCard;
import com.bader.domain.payment.provider.PaymentProvider;

public class PaypalBasedPaymentProvider implements PaymentProvider {
    @Override
    public boolean attemptPayment(CreditCard creditCard, Integer amountInCents) {
        /*
        Integrate with the Paypal API to make the right calls, check the right things and so on so the details
        are abstracted away for users of the PaymentProvider interface
         */
        System.out.println("***************************************************************************************\n");
        System.out.println("PaypalBasedPaymentProvider called for a payment of " + ((float) amountInCents) / 100.0 + "€ for card owner " + creditCard.getOwnerName());
        System.out.println("\n***************************************************************************************");
        return true;
    }
}
