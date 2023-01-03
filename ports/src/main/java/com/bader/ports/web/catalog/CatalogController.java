package com.bader.ports.web.catalog;

import com.bader.domain.catalog.CatalogService;
import com.bader.domain.catalog.model.Car;
import com.bader.ports.web.catalog.dto.request.AddCar;
import com.bader.ports.web.catalog.dto.response.CarResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    CatalogService catalogService;

    CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping()
    public List<CarResponse> getCatalog() {
        return catalogService.getCatalog().stream().map(this::toCarResponse).collect(Collectors.toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse addCar(@RequestBody @Valid AddCar addCar) {
        return toCarResponse(catalogService.addCar(addCar.getModel(), addCar.getBrand(), addCar.getColor(), addCar.getYear(), addCar.getPrice()));
    }

    private CarResponse toCarResponse(Car car) {
        return new CarResponse(car);
    }
}
