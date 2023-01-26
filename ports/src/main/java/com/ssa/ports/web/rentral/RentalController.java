package com.ssa.ports.web.rentral;

import com.ssa.domain.rental.RentalService;
import com.ssa.domain.rental.model.CartEntry;
import com.ssa.domain.rental.model.Reservation;
import com.ssa.ports.web.rentral.dto.request.CartEntryRequest;
import com.ssa.ports.web.rentral.dto.request.CartPaymentRequest;
import com.ssa.ports.web.rentral.dto.request.ReservationRequest;
import com.ssa.ports.web.rentral.dto.response.AnonymousReservationResponse;
import com.ssa.ports.web.rentral.dto.response.CartEntryResponse;
import com.ssa.ports.web.rentral.dto.response.ReservationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rental")
public class RentalController {
    private final Date maxSearchDate;
    private final Date minSearchDate;

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) throws ParseException {
        this.rentalService = rentalService;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        maxSearchDate = format.parse("9999-12-31");
        minSearchDate = format.parse("0000-01-01");
    }

    @GetMapping("/cart")
    public List<CartEntryResponse> getCart(Principal customer) {
        return rentalService.getCart(customer.getName()).stream().map(this::toCartEntryResponse).collect(Collectors.toList());
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartEntryResponse> addCartEntry(@RequestBody @Valid CartEntryRequest request, Principal customer) {
        Optional<CartEntryResponse> cartEntryResponse = rentalService.addCartEntry(customer.getName(), request.getCarId(), request.getStartDate(), request.getEndDate()).map(this::toCartEntryResponse);
        if (cartEntryResponse.isPresent()) {
            return ResponseEntity.of(cartEntryResponse);
        }
        return ResponseEntity.status(409).build();
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

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody @Valid ReservationRequest request, Principal agent) {
        Optional<ReservationResponse> reservationResponse = rentalService.addReservation(request.getCustomerId(), request.getCarId(), request.getStartDate(), request.getEndDate(), request.getPaid()).map(this::toReservationResponse);
        if (reservationResponse.isPresent()) {
            return ResponseEntity.of(reservationResponse);
        }
        return ResponseEntity.status(409).build();
    }

    @PutMapping("/reservations/{reservationId}/pay")
    @ResponseStatus(HttpStatus.OK)
    public void payReservationOnSite(@PathVariable UUID reservationId, Principal agent) {
        rentalService.payReservationOnSite(reservationId);
    }

    @GetMapping("/car/{carId}/reservations")
    public List<AnonymousReservationResponse> getReservationsBetweenForCar(@PathVariable("carId") UUID carId, @RequestParam("searchStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> searchStartDate, @RequestParam("searchEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> searchEndDate) {
        return rentalService.getReservationsBetweenForCar(carId, searchStartDate.orElse(minSearchDate), searchEndDate.orElse(maxSearchDate)).stream().map(this::toAnonymousReservationResponse).collect(Collectors.toList());
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
