package com.bader.ports.web.rentral;

import com.bader.domain.rental.RentalService;
import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.ports.web.rentral.dto.request.CartEntryRequest;
import com.bader.ports.web.rentral.dto.request.CartPaymentRequest;
import com.bader.ports.web.rentral.dto.response.AnonymousReservationResponse;
import com.bader.ports.web.rentral.dto.response.CartEntryResponse;
import com.bader.ports.web.rentral.dto.response.ReservationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rental")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/cart")
    public List<CartEntryResponse> getCart(Principal customer) {
        return rentalService.getCart(customer.getName()).stream().map(this::toCartEntryResponse).collect(Collectors.toList());
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartEntryResponse> addCartEntry(@RequestBody @Valid CartEntryRequest request, Principal customer) {
        return ResponseEntity.of(
                rentalService.addCartEntry(customer.getName(), request.getCarId(), request.getStartDate(), request.getEndDate()).map(this::toCartEntryResponse)
        );
    }

    @DeleteMapping("/cart/{cartEntryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCartEntry(@PathVariable("cartEntryId") UUID cartEntryId, Principal customer) {
        if (rentalService.deleteCartEntry(customer.getName(), cartEntryId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCartEntry(Principal customer) {
        if (rentalService.deleteCart(customer.getName())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cart/pay")
    public ResponseEntity<Void> payCart(@RequestBody @Valid CartPaymentRequest request, Principal customer) {
        if (rentalService.payCart(customer.getName(), request.getCardNumber(), request.getSecurityCode(), request.getExpirationDate(), request.getOwnerName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> getCustomerReservations(@RequestParam("timeCriteria") Optional<String> timeCriteria, Principal customer) {
        if (timeCriteria.orElse("").equalsIgnoreCase("future")) {
            return rentalService.getFutureReservationsForCustomer(customer.getName()).stream().map(this::toReservationResponse).collect(Collectors.toList());
        } else if (timeCriteria.orElse("").equalsIgnoreCase("past")) {
            return rentalService.getPastReservationsForCustomer(customer.getName()).stream().map(this::toReservationResponse).collect(Collectors.toList());
        } else {
            return rentalService.getAllReservationsForCustomer(customer.getName()).stream().map(this::toReservationResponse).collect(Collectors.toList());
        }
    }

    @GetMapping("/car/{carId}/reservations")
    public List<AnonymousReservationResponse> getFutureAndCurrentReservationsForCar(@PathVariable("carId") UUID carId) {
        return rentalService.getFutureAndCurrentReservationsForCar(carId).stream().map(this::toAnonymousReservationResponse).collect(Collectors.toList());
    }

    private CartEntryResponse toCartEntryResponse(CartEntry cartEntry) {
        return new CartEntryResponse(cartEntry);
    }

    private AnonymousReservationResponse toAnonymousReservationResponse(Reservation reservation) {
        return new AnonymousReservationResponse(reservation);
    }

    private ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(reservation);
    }
}
