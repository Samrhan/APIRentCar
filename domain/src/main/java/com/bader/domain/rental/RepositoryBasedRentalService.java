package com.bader.domain.rental;

import com.bader.domain.payment.PaymentService;
import com.bader.domain.payment.model.CreditCard;
import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.CartEntryRepository;
import com.bader.domain.rental.repository.ReservationRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedRentalService implements RentalService {

    private final CartEntryRepository cartEntryRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public RepositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository, PaymentService paymentService) {
        this.cartEntryRepository = cartEntryRepository;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
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
        if(isCarAvailableBetween(carId, startDate, endDate)){
            return Optional.ofNullable(this.cartEntryRepository.addCartEntry(associatedUserUsername, carId, startDate, endDate));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCartEntry(String associatedUserUsername, UUID cartEntryId) {
        return this.cartEntryRepository.deleteCartEntry(associatedUserUsername, cartEntryId);
    }

    @Override
    public List<Reservation> getFutureReservationsForCar(UUID carId) {
        return this.reservationRepository.getFutureReservationsForCar(carId);
    }

    @Override
    public boolean payCart(String associatedUserUsername, String cardNumber, String securityCode, String expirationDate, String ownerName) {
        List<CartEntry> cart = this.cartEntryRepository.getCart(associatedUserUsername);
        if (cart.size() < 1){
            return false;
        }

        Integer cartTotalInCents = computeCartTotalInCents(cart);
        if (paymentService.attemptPayment(new CreditCard(cardNumber, securityCode, expirationDate, ownerName), cartTotalInCents)){
            this.cartEntryRepository.deleteCart(associatedUserUsername);
            this.reservationRepository.convertCartToReservationsAfterPayment(cart);
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

    private boolean isCarAvailableBetween(UUID carId, Date startDate, Date endDate) {
        List<Reservation> carReservations = this.reservationRepository.getReservationsBetweenForCar(carId, startDate, endDate);
        return carReservations.size() == 0;
    }
}
