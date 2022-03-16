package rosjose.demo.rates.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rosjose.demo.rates.dao.Rate;
import rosjose.demo.rates.dto.ProductRate;
import rosjose.demo.rates.dto.ProductRatePrice;
import rosjose.demo.rates.service.RatesService;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RequestMapping(path = "/v1", consumes = APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
@Tags({
        @Tag(name = "Rates Controller", description = "Provides operation to insert, update, delete and query rates")
})
public class RatesController {

    private final RatesService service;

    @Operation(
            description = "Creates a new Rate",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success creation")
            })
    @PostMapping("/rates")
    public Rate createRate(@RequestBody Rate rate) {
        return this.service.createRate(rate);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Success deletion")
    })
    @DeleteMapping("/rates/{id}")
    public void deleteRate(@PathVariable Long id) {
        this.service.deleteRate(id);
    }

    @GetMapping("/rates")
    public Stream<ProductRate> getRates() {
        return service.getProductRates();
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    @GetMapping("/rates/{id}")
    public ProductRate getRate(@PathVariable Long id) {
        return service.getProductRateById(id).orElseThrow(RateNotFound::new);
    }

    @PutMapping("/rates/{id}/updatePrice")
    public Rate updateRatePrice(@PathVariable Long id, @RequestBody ProductRatePrice newPrice) {
        return this.service.updateProductRatePrice(id, newPrice).orElseThrow(RateNotFound::new);
    }

    @GetMapping(path = "/rates", params = {"date", "brandId", "productId"})
    public ProductRate getProductRateByDateProductBrand(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam Long brandId, @RequestParam Long productId) {
        return this.service.getProductRateByDateProductBrand(date, brandId, productId).orElseThrow(RateNotFound::new);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class RateNotFound extends RuntimeException {
        public RateNotFound() {
            super("Rate not found");
        }
    }

}
