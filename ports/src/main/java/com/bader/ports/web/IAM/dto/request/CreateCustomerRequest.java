package com.bader.ports.web.IAM.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateCustomerRequest {

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @NotBlank
    private final String phone;

    @NotBlank
    @Email
    private final String email;

    @JsonCreator
    public CreateCustomerRequest(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("phone") String phone, @JsonProperty("email") String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
