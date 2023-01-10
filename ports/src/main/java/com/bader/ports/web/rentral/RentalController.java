package com.bader.ports.web.rentral;

import com.bader.domain.rental.RentalService;
import com.bader.domain.rental.model.CartEntry;
import com.bader.ports.web.rentral.dto.response.CartEntryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    private CartEntryResponse toCartEntryResponse(CartEntry cartEntry) {
        return new CartEntryResponse(cartEntry);
    }
}
