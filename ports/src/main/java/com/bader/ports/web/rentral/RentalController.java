package com.bader.ports.web.rentral;

import com.bader.domain.rental.RentalService;
import com.bader.domain.rental.model.CartEntry;
import com.bader.ports.web.rentral.dto.request.CartEntryRequest;
import com.bader.ports.web.rentral.dto.response.CartEntryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<CartEntryResponse> getCart() {
        return rentalService.getCart().stream().map(this::toCartEntryResponse).collect(Collectors.toList());
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public CartEntryResponse addCartEntry(@RequestBody @Valid CartEntryRequest request) {
        return toCartEntryResponse(rentalService.addCartEntry(request.getCarId(), request.getStartDate(), request.getEndDate()));
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
    public ResponseEntity<Void> deleteCartEntry(){
        if (rentalService.deleteCart()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CartEntryResponse toCartEntryResponse(CartEntry cartEntry) {
        return new CartEntryResponse(cartEntry);
    }
}
