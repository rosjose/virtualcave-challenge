package rosjose.demo.rates.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Jacksonized
@Builder
@Value
@With
public class ProductRatePrice {
    private int price;

    private int priceScale;
}
