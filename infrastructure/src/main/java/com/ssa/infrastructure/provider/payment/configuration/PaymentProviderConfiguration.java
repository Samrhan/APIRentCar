package com.ssa.infrastructure.provider.payment.configuration;

import com.ssa.domain.payment.ports.PaymentProvider;
import com.ssa.infrastructure.provider.payment.PaypalBasedPaymentProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaymentProviderConfiguration { // Need to configure the provider manually because we can't use the @Repository annotation
    @Bean
    public PaymentProvider paymentProvider() {
        /* We choose to use the PaypalBasedPaymentProvider now,
           but this would be easy to change in the future : simply change this line to
           instantiate a StripeBasedPaymentProvider instead, and the rest of the code doesn't need to change
        */
        return new PaypalBasedPaymentProvider();
    }
}
