package com.bader.infrastructure.persitence.user.entity;

import com.bader.domain.user.model.Customer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.*;

import java.util.UUID;

@Entity(name = "CustomerEntity")
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @OneToOne(targetEntity = UserEntity.class, optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity associatedUser;

    @PersistenceCreator
    public CustomerEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CustomerEntity() {
    }

    public CustomerEntity(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer toModel(){
        return new Customer(id, firstName, lastName, associatedUser.toModel());
    }

    public void setAssociatedUser(UserEntity associatedUser) {
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

    public UserEntity getAssociatedUser() {
        return associatedUser;
    }
}
