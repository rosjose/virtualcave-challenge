package rosjose.demo.currencies.client;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
@With
public class Currency {
    String symbol;
    String code;
    int decimals;
}
