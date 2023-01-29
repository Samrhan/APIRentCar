package com.ssa.adapters.persitence.rental;

import com.ssa.adapters.persitence.IAM.JPACustomerRepository;
import com.ssa.adapters.persitence.IAM.entity.CustomerEntity;
import com.ssa.adapters.persitence.catalog.JPACatalogRepository;
import com.ssa.adapters.persitence.catalog.entity.CarEntity;
import com.ssa.adapters.persitence.rental.entity.ReservationEntity;
import com.ssa.domain.rental.model.CartEntry;
import com.ssa.domain.rental.model.Reservation;
import com.ssa.domain.rental.ports.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
        Optional<CarEntity> car = jpaCatalogRepository.findById(carId);
        if (car.isEmpty()) {
            return new ArrayList<>();
        }

        Optional<List<ReservationEntity>> reservations = jpaReservationRepository.findAllForCarBetween(car.get(), startDate, endDate);
        return unwrapReservationEntityListOptional(reservations);
    }

    @Override
    public List<Reservation> convertCartToReservationsAfterPayment(List<CartEntry> cart) {
        if (cart.size() < 1) {
            return new ArrayList<>();
        }

        // Avoid fetching the customer N times when we know that it's always the same one
        Optional<CustomerEntity> customerOptional = jpaCustomerRepository.findById(cart.get(0).getCustomer().getId());
        if (customerOptional.isEmpty()) {
            return new ArrayList<>();
        }
        CustomerEntity customer = customerOptional.get();

        ArrayList<Reservation> reservations = new ArrayList<>();
        for (CartEntry cartEntry : cart) {
            reservations.add(convertCartEntryToPaidReservation(cartEntry, customer));
        }
        return reservations;
    }

    private Reservation convertCartEntryToPaidReservation(CartEntry cartEntry, CustomerEntity customer) {
        Optional<CarEntity> car = jpaCatalogRepository.findById(cartEntry.getCar().getId());
        if (car.isPresent()) {
            ReservationEntity reservation = new ReservationEntity(customer, car.get(), cartEntry.getStartDate(), cartEntry.getEndDate(), true);
            jpaReservationRepository.save(reservation);
            return reservation.toModel();
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationsForCustomerAfter(String associatedUserUsername, Date date) {
        Optional<CustomerEntity> customer = jpaCustomerRepository.findByEmail(associatedUserUsername);
        if (customer.isEmpty()) {
            return new ArrayList<>();
        }

        Optional<List<ReservationEntity>> reservations = jpaReservationRepository.findAllForCustomerAfter(customer.get(), date);
        return unwrapReservationEntityListOptional(reservations);
    }

    @Override
    public List<Reservation> getReservationsForCustomerBefore(String associatedUserUsername, Date date) {
        Optional<CustomerEntity> customer = jpaCustomerRepository.findByEmail(associatedUserUsername);
        if (customer.isEmpty()) {
            return new ArrayList<>();
        }

        Optional<List<ReservationEntity>> reservations = jpaReservationRepository.findAllForCustomerBefore(customer.get(), date);
        return unwrapReservationEntityListOptional(reservations);
    }

    public List<Reservation> unwrapReservationEntityListOptional(Optional<List<ReservationEntity>> reservationEntitiesOptional) {
        return reservationEntitiesOptional.map(
                reservationEntities -> reservationEntities.stream().map(ReservationEntity::toModel).collect(Collectors.toList())
        ).orElseGet(ArrayList::new);
    }

    @Override
    public Reservation addReservation(UUID customerId, UUID carId, Date startDate, Date endDate, Boolean paid) {
        Optional<CustomerEntity> customerEntityOptional = this.jpaCustomerRepository.findById(customerId);
        Optional<CarEntity> carEntityOptional = this.jpaCatalogRepository.findById(carId);
        if (customerEntityOptional.isPresent() && carEntityOptional.isPresent()) {
            CustomerEntity customer = customerEntityOptional.get();
            CarEntity car = carEntityOptional.get();
            ReservationEntity reservation = new ReservationEntity(customer, car, startDate, endDate, paid);
            jpaReservationRepository.save(reservation);
            return reservation.toModel();
        }
        return null;
    }

    @Override
    public Optional<Reservation> payReservation(UUID reservationId) {
        Optional<ReservationEntity> reservationEntityOptional = this.jpaReservationRepository.findById(reservationId);
        if (reservationEntityOptional.isPresent()) {
            ReservationEntity reservation = reservationEntityOptional.get();
            reservation.setPaid(true);
            this.jpaReservationRepository.save(reservation);
            return reservationEntityOptional.map(ReservationEntity::toModel);
        }
        return Optional.empty();
    }
}
