package com.ssa.controllers.http.catalog;

import com.ssa.controllers.http.catalog.dto.request.CarRequest;
import com.ssa.controllers.http.catalog.dto.response.CarResponse;
import com.ssa.controllers.http.security.SecurityConfiguration;
import com.ssa.domain.catalog.CatalogService;
import com.ssa.domain.catalog.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ssa.controllers.http.security.SecurityConfiguration.PRE_AUTHORIZE_SELLER;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping()
    public List<CarResponse> getCatalog() {
        return catalogService.getCatalog().stream().map(this::toCarResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") UUID id) {
        return ResponseEntity.of(
                catalogService.getCar(id).map(this::toCarResponse)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize(PRE_AUTHORIZE_SELLER)
    public ResponseEntity<CarResponse> updateCar(@PathVariable("id") UUID id, @RequestBody @Valid CarRequest carRequest) {
        return ResponseEntity.of(
                catalogService.updateCar(id, carRequest.getModel(), carRequest.getBrand(), carRequest.getColor(), carRequest.getYear(), carRequest.getPrice()).map(this::toCarResponse)
        );
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({SecurityConfiguration.SELLER, SecurityConfiguration.ADMIN})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(PRE_AUTHORIZE_SELLER)
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID id) {
        // If the deletion succeeds, return 204 as there is now no content. Otherwise, return 404 since the entity doesn't exist
        if (this.catalogService.deleteCar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(PRE_AUTHORIZE_SELLER)
    public CarResponse addCar(@RequestBody @Valid CarRequest carRequest) {
        return toCarResponse(catalogService.addCar(carRequest.getModel(), carRequest.getBrand(), carRequest.getColor(), carRequest.getYear(), carRequest.getPrice()));
    }

    private CarResponse toCarResponse(Car car) {
        return new CarResponse(car);
    }
}
