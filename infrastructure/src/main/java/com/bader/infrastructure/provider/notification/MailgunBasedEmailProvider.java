package com.bader.infrastructure.provider.notification;

import com.bader.domain.notification.provider.EmailProvider;

/**
 * An implementation of EmailProvider based on Mailgun, a company that provides an API to easily send emails : https://www.mailgun.com/
 */
public class MailgunBasedEmailProvider implements EmailProvider {
    @Override
    public void sendEmail(String address, String content) {
        // Here we should implement the right calls to Mailgun's API to send the email
        // If we wanted to use another provider, or to create or own mail server, we would just need to create another class implementing EmailProvider
        System.out.println("***************************************************************************************\n");
        System.out.println("Sent email to " + address + " with the following content :\n" + content);
        System.out.println("\n***************************************************************************************");

    }
}
