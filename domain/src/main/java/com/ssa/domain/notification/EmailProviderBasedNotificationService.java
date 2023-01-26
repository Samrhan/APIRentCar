package com.ssa.domain.notification;

import com.ssa.domain.notification.ports.EmailProvider;

public class EmailProviderBasedNotificationService implements NotificationService {
    private final EmailProvider emailProvider;

    public EmailProviderBasedNotificationService(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @Override
    public void notifyCustomer(String associatedUserUsername, Integer cartTotalInCents) {
        emailProvider.sendEmail(associatedUserUsername, "Receipt for a reservation totaling " + cartTotalInCents / 100.0 + "€.");
    }
}
