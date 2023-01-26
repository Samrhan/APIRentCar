package com.ssa.domain.notification.ports;

public interface EmailProvider {
    void sendEmail(String adress, String content);
}
