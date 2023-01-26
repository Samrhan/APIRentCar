package com.bader.infrastructure.persitence.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.ReservationRepository;
import com.bader.domain.user.model.Customer;
import com.bader.infrastructure.persitence.catalog.JPABasedCatalogRepository;
import com.bader.infrastructure.persitence.catalog.JPACatalogRepository;
import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import com.bader.infrastructure.persitence.rental.entity.ReservationEntity;
import com.bader.infrastructure.persitence.user.JPABasedCustomerRepository;
import com.bader.infrastructure.persitence.user.JPACustomerRepository;
import com.bader.infrastructure.persitence.user.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JPABasedReservationRepository implements ReservationRepository {
    private final JPAReservationRepository jpaReservationRepository;

    private final JPACustomerRepository jpaCustomerRepository;
    private final JPACatalogRepository jpaCatalogRepository;

    public JPABasedReservationRepository(JPAReservationRepository jpaReservationRepository, JPACustomerRepository jpaCustomerRepository, JPACatalogRepository jpaCatalogRepository) {
        this.jpaReservationRepository = jpaReservationRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.jpaCatalogRepository = jpaCatalogRepository;
    }

    @Override
    public List<Reservation> getReservationsBetweenForCar(UUID carId, Date startDate, Date endDate) {
        return new ArrayList<>();
    }

    @Override
    public List<Reservation> getFutureReservationsForCar(UUID carId) {
        return new ArrayList<>();
    }

    @Override
    public void convertCartToReservationsAfterPayment(List<CartEntry> cart) {
        if (cart.size() < 1) {
            return;
        }

        // Avoid fetching the customer N times when we know that it's always the same one
        Optional<CustomerEntity> customerOptional = jpaCustomerRepository.findById(cart.get(0).getCustomer().getId());
        if (customerOptional.isEmpty()) {
            return;
        }
        CustomerEntity customer = customerOptional.get();

        for(CartEntry cartEntry : cart){
            convertCartEntryToPaidReservation(cartEntry, customer);
        }
    }

    private void convertCartEntryToPaidReservation(CartEntry cartEntry, CustomerEntity customer) {
        Optional<CarEntity> car = jpaCatalogRepository.findById(cartEntry.getCar().getId());
        if (car.isPresent()){
            ReservationEntity reservation = new ReservationEntity(customer, car.get(), cartEntry.getStartDate(), cartEntry.getEndDate(), true);
            jpaReservationRepository.save(reservation);
        }
    }
}
