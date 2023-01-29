package com.ssa.adapters.providers.notification.configuration;

import com.ssa.domain.notification.EmailProviderBasedNotificationService;
import com.ssa.domain.notification.ports.EmailProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationServiceConfiguration {
    @Bean
    public EmailProviderBasedNotificationService emailProviderBasedNotificationService(EmailProvider emailProvider) {
        return new EmailProviderBasedNotificationService(emailProvider);
    }
}
