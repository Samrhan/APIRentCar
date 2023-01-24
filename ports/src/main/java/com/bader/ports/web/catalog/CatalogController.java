package com.bader.ports.web.catalog;

import com.bader.domain.catalog.CatalogService;
import com.bader.domain.catalog.model.Car;
import com.bader.ports.web.catalog.dto.request.CarRequest;
import com.bader.ports.web.catalog.dto.response.CarResponse;
import com.bader.ports.web.security.SecurityConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

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
    public ResponseEntity<CarResponse> updateCar(@PathVariable("id") UUID id, @RequestBody @Valid CarRequest carRequest) {
        return ResponseEntity.of(
                catalogService.updateCar(id, carRequest.getModel(), carRequest.getBrand(), carRequest.getColor(), carRequest.getYear(), carRequest.getPrice()).map(this::toCarResponse)
        );
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({SecurityConfiguration.SELLER, SecurityConfiguration.ADMIN})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID id){
        // If the deletion succeeds, return 204 as there is now no content. Otherwise, return 404 since the entity doesn't exist
        if (this.catalogService.deleteCar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse addCar(@RequestBody @Valid CarRequest carRequest, Principal principal) {
        return toCarResponse(catalogService.addCar(carRequest.getModel(), carRequest.getBrand(), carRequest.getColor(), carRequest.getYear(), carRequest.getPrice()));
    }

    private CarResponse toCarResponse(Car car) {
        return new CarResponse(car);
    }
}
