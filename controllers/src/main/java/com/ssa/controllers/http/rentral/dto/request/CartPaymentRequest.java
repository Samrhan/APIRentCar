package com.ssa.controllers.http.rentral.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartPaymentRequest {
    private final String cardNumber;
    private final String securityCode;
    private final String expirationDate;
    private final String ownerName;

    @JsonCreator
    public CartPaymentRequest(@JsonProperty("cardNumber") String cardNumber, @JsonProperty("securityCode") String securityCode, @JsonProperty("expirationDate") String expirationDate, @JsonProperty("ownerName") String ownerName) {
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
