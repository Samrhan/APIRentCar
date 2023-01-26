package com.bader.infrastructure.provider.notification.service;

import com.bader.domain.notification.EmailProviderBasedNotificationService;
import com.bader.domain.notification.ports.EmailProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationServiceConfiguration {
    @Bean
    public EmailProviderBasedNotificationService emailProviderBasedNotificationService(EmailProvider emailProvider) {
        return new EmailProviderBasedNotificationService(emailProvider);
    }
}
