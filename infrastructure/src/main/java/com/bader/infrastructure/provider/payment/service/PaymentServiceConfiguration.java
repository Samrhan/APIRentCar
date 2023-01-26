package com.bader.infrastructure.provider.payment.service;

import com.bader.infrastructure.provider.payment.PaypalBasedPaymentProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceConfiguration {
    @Bean
    public PaypalBasedPaymentProvider paypalBasedPaymentProvider(){
        return new PaypalBasedPaymentProvider();
    }
}
