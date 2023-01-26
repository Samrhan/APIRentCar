package com.bader.infrastructure.provider.payment.service;

import com.bader.domain.payment.PaymentService;
import com.bader.domain.payment.ProviderBasedPaymentService;
import com.bader.domain.payment.ports.PaymentProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfiguration {
    @Bean
    public PaymentService providerBasedPaymentService(PaymentProvider paymentProvider) {
        return new ProviderBasedPaymentService(paymentProvider);
    }
}
