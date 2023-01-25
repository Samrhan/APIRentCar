package com.bader.ports.web.rentral;

import com.bader.domain.rental.RentalService;
import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.ports.web.rentral.dto.request.CartEntryRequest;
import com.bader.ports.web.rentral.dto.response.AnonymousReservationResponse;
import com.bader.ports.web.rentral.dto.response.CartEntryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
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
    public ResponseEntity<Void> deleteCartEntry(@PathVariable("cartEntryId")UUID cartEntryId){
        if (rentalService.deleteCartEntry(cartEntryId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCartEntry(Principal customer){
        if (rentalService.deleteCart(customer.getName())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/car/{carId}/reservations")
    public List<AnonymousReservationResponse> getFutureReservationsForCar(@PathVariable("carId") UUID carId){
        return rentalService.getFutureReservationsForCar(carId).stream().map(this::toAnonymousReservationResponse).collect(Collectors.toList());
    }

    private CartEntryResponse toCartEntryResponse(CartEntry cartEntry) {
        return new CartEntryResponse(cartEntry);
    }
    private AnonymousReservationResponse toAnonymousReservationResponse(Reservation reservation){
        return new AnonymousReservationResponse(reservation);
    }
}
