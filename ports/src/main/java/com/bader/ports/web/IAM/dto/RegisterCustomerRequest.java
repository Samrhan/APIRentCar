package com.bader.ports.web.IAM.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class RegisterCustomerRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @JsonCreator
    public RegisterCustomerRequest(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
