package com.ssa.adapters.providers.notification.configuration;

import com.ssa.adapters.providers.notification.MailgunBasedEmailProvider;
import com.ssa.domain.notification.ports.EmailProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationProviderConfiguration { // Need to configure the provider manually because we can't use the @Repository annotation
    @Bean
    public EmailProvider emailProvider() {
        /* We choose to use the MailgunBasedEmailProvider now,
           but this would be easy to change in the future : simply change this line to
           instantiate another class implementing the EmailProvider instead, and the rest of the code doesn't need to change
        */
        return new MailgunBasedEmailProvider();
    }
}
