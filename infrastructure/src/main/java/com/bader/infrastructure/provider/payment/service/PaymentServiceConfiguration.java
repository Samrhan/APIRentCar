package com.bader.infrastructure.provider.payment.service;

import com.bader.domain.payment.provider.PaymentProvider;
import com.bader.infrastructure.provider.payment.PaypalBasedPaymentProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfiguration {
    @Bean
    public PaymentProvider paymentProvider() {
        /* We choose to use the PaypalBasedPaymentProvider now,
           but this would be easy to change in the future : simply change this line to
           instantiate a StripeBasedPaymentProvider instead, and the rest of the code doesn't need to change
        */
        return new PaypalBasedPaymentProvider();
    }
}
