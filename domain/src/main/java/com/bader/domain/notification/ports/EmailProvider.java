package com.bader.domain.notification.ports;

public interface EmailProvider {
    void sendEmail(String adress, String content);
}
