package com.ssa.ports.web.search;

import com.ssa.domain.catalog.model.Car;
import com.ssa.domain.search.SearchService;
import com.ssa.ports.web.catalog.dto.response.CarResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {
    SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public List<CarResponse> searchCar(@RequestParam("model") Optional<String> model, @RequestParam("brand") Optional<String> brand, @RequestParam("color") Optional<String> color, @RequestParam("year") Optional<Integer> year, @RequestParam("price-min") Optional<BigDecimal> priceMin, @RequestParam("price-max") Optional<BigDecimal> priceMax) {
        return searchService
                .searchCar(
                        model.orElse(null),
                        brand.orElse(null),
                        color.orElse(null),
                        year.orElse(null),
                        priceMin.orElse(null),
                        priceMax.orElse(null)
                )
                .stream()
                .map(this::toCarResponse)
                .collect(Collectors.toList());
    }

    private CarResponse toCarResponse(Car car) {
        return new CarResponse(car);
    }
}
