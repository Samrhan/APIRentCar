package com.ssa.domain.notification;

public interface NotificationService {
    void notifyCustomer(String associatedUserUsername, Integer cartTotalInCents);
}
