package com.bader.domain.notification.provider;

public interface EmailProvider {
    public void sendEmail(String adress, String content);
}
