package com.ssa.domain.payment.model;

public class CreditCard {
    private final String cardNumber;
    private final String securityCode;
    private final String expirationDate;
    private final String ownerName;

    public CreditCard(String cardNumber, String securityCode, String expirationDate, String ownerName) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
        this.ownerName = ownerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
