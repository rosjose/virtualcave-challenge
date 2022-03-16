package rosjose.demo.rates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
public class ProductRate {

    @Schema(example = "100")
    Long id;

    @Schema(example = "1")
    Long brandId;

    @Schema(example = "1")
    Long productId;

    @Schema(example = "2022-01-01")
    LocalDate startDate;

    @Schema(example = "2022-01-31")
    LocalDate endDate;

    @Schema(example = "30.99")
    BigDecimal price;

    @Schema(example = "£ 30.99")
    String formattedPrice;

    @Schema(example = "GBP")
    String currencyCode;
}
