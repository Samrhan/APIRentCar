package com.ssa.domain.IAM.model;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    private final User associatedUser;

    public Customer(UUID id, String firstName, String lastName, User associatedUser) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.associatedUser = associatedUser;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User getAssociatedUser() {
        return associatedUser;
    }
}
