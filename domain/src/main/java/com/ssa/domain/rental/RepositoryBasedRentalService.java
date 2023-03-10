package com.ssa.domain.rental;

import com.ssa.domain.notification.NotificationService;
import com.ssa.domain.payment.PaymentService;
import com.ssa.domain.payment.model.CreditCard;
import com.ssa.domain.rental.model.CartEntry;
import com.ssa.domain.rental.model.Reservation;
import com.ssa.domain.rental.ports.CartEntryRepository;
import com.ssa.domain.rental.ports.ReservationPreparationSDK;
import com.ssa.domain.rental.ports.ReservationRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepositoryBasedRentalService implements RentalService {

    private final CartEntryRepository cartEntryRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    private final ReservationPreparationSDK reservationPreparationSDK;

    public RepositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository, PaymentService paymentService, NotificationService notificationService, ReservationPreparationSDK reservationPreparationSDK) {
        this.cartEntryRepository = cartEntryRepository;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        this.reservationPreparationSDK = reservationPreparationSDK;
    }

    @Override
    public List<CartEntry> getCart(String associatedUserUsername) {
        return this.cartEntryRepository.getCart(associatedUserUsername);
    }

    @Override
    public boolean deleteCart(String associatedUserUsername) {
        return this.cartEntryRepository.deleteCart(associatedUserUsername);
    }

    @Override
    public Optional<CartEntry> addCartEntry(String associatedUserUsername, UUID carId, Date startDate, Date endDate) {
        if (isCarAvailableBetween(carId, startDate, endDate)) {
            return Optional.ofNullable(this.cartEntryRepository.addCartEntry(associatedUserUsername, carId, startDate, endDate));
        }
        return Optional.empty();
    }

    private boolean isCarAvailableBetween(UUID carId, Date startDate, Date endDate) {
        List<Reservation> carReservations = this.reservationRepository.getReservationsBetweenForCar(carId, startDate, endDate);
        return carReservations.size() == 0;
    }

    @Override
    public boolean deleteCartEntry(String associatedUserUsername, UUID cartEntryId) {
        return this.cartEntryRepository.deleteCartEntry(associatedUserUsername, cartEntryId);
    }

    @Override
    public List<Reservation> getReservationsBetweenForCar(UUID carId, Date searchStartDate, Date searchEndDate) {
        return this.reservationRepository.getReservationsBetweenForCar(carId, searchStartDate, searchEndDate);
    }

    @Override
    public boolean payCart(String associatedUserUsername, String cardNumber, String securityCode, String expirationDate, String ownerName) {
        List<CartEntry> cart = this.cartEntryRepository.getCart(associatedUserUsername);
        if (cart.size() < 1) {
            return false;
        }

        Integer cartTotalInCents = computeCartTotalInCents(cart);
        if (paymentService.attemptPayment(new CreditCard(cardNumber, securityCode, expirationDate, ownerName), cartTotalInCents)) {
            this.cartEntryRepository.deleteCart(associatedUserUsername);
            List<Reservation> reservations = this.reservationRepository.convertCartToReservationsAfterPayment(cart);
            for (Reservation reservation : reservations) {
                this.reservationPreparationSDK.prepareReservation(reservation);
            }
            this.notificationService.notifyCustomer(associatedUserUsername, cartTotalInCents);
            return true;
        }
        return false;
    }

    private Integer computeCartTotalInCents(List<CartEntry> cart) {
        return cart.stream().reduce(0, (subtotal, cartEntry) -> subtotal + computeCartEntryPriceInCents(cartEntry), Integer::sum);
    }

    private Integer computeCartEntryPriceInCents(CartEntry cartEntry) {
        int reservationNumberOfDays = cartEntry.getReservationDurationInDays();
        return cartEntry.getCar()
                .getPrice()
                .multiply(new BigDecimal(reservationNumberOfDays))
                .multiply(new BigDecimal(100))
                .intValue();
    }

    @Override
    public List<Reservation> getFutureReservationsForCustomer(String associatedUserUsername) {
        return this.reservationRepository.getReservationsForCustomerAfter(associatedUserUsername, new Date());
    }

    @Override
    public List<Reservation> getPastReservationsForCustomer(String associatedUserUsername) {
        return this.reservationRepository.getReservationsForCustomerBefore(associatedUserUsername, new Date());
    }

    @Override
    public List<Reservation> getAllReservationsForCustomer(String associatedUserUsername) {
        return Stream
                .concat(
                        getPastReservationsForCustomer(associatedUserUsername).stream(),
                        getFutureReservationsForCustomer(associatedUserUsername).stream()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Reservation> addReservation(UUID customerId, UUID carId, Date startDate, Date endDate, Boolean paid) {
        if (isCarAvailableBetween(carId, startDate, endDate)) {
            return Optional.ofNullable(this.reservationRepository.addReservation(customerId, carId, startDate, endDate, paid));
        }
        return Optional.empty();
    }

    @Override
    public void payReservationOnSite(UUID reservationId) {
        Optional<Reservation> reservationOptional = this.reservationRepository.payReservation(reservationId);
        if (reservationOptional.isEmpty()) {
            return;
        }

        Integer cartTotalInCents = computeReservationPriceInCents(reservationOptional.get());
        this.reservationPreparationSDK.prepareReservation(reservationOptional.get());
        this.notificationService.notifyCustomer(reservationOptional.get().getCustomer().getAssociatedUser().getUsername(), cartTotalInCents);
    }

    private Integer computeReservationPriceInCents(Reservation reservation) {
        int reservationNumberOfDays = reservation.getReservationDurationInDays();
        return reservation.getCar()
                .getPrice()
                .multiply(new BigDecimal(reservationNumberOfDays))
                .multiply(new BigDecimal(100))
                .intValue();
    }
}
