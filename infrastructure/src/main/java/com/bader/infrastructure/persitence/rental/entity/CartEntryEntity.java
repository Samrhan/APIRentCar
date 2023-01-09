package com.bader.infrastructure.persitence.rental.entity;

import com.bader.domain.rental.model.CartEntry;
import com.bader.infrastructure.persitence.IAM.entity.CustomerEntity;
import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "CartEntryEntity")
public class CartEntryEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @PersistenceCreator

    public CartEntryEntity(CustomerEntity customer, CarEntity car, Date startDate, Date endDate) {
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CartEntryEntity() {
    }

    public CartEntryEntity(UUID id, CustomerEntity customer, CarEntity car, Date startDate, Date endDate) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CartEntry toModel(){
        return new CartEntry(customer.toModel(), car.toModel(), startDate, endDate);
    }
}
