package com.ssa.adapters.providers.payment.configuration;

import com.ssa.domain.payment.PaymentService;
import com.ssa.domain.payment.ProviderBasedPaymentService;
import com.ssa.domain.payment.ports.PaymentProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfiguration {
    @Bean
    public PaymentService providerBasedPaymentService(PaymentProvider paymentProvider) {
        return new ProviderBasedPaymentService(paymentProvider);
    }
}
