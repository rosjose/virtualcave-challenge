package rosjose.demo.rates.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rosjose.demo.currencies.client.Currency;
import rosjose.demo.currencies.client.CurrencyClient;
import rosjose.demo.currencies.client.UnexpectedCurrencyClientException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyClient client;


    /**
     * Formats a price according to the specification provided by the external currency service.
     *
     * @param currencyCode
     * @param price
     * @return The string representation of a currency value
     */
    public String formatValue(String currencyCode, BigDecimal price) throws UnformattableValueException{
        try {
            Optional<Currency> currencySpec = this.client.getCurrency(currencyCode);
            return currencySpec
                    .flatMap(currency -> Optional.of(currency.getSymbol() + " " + price.setScale(currency.getDecimals())))
                    .orElseThrow(UnformattableValueException::new);
        }catch (UnexpectedCurrencyClientException e){
            throw new UnformattableValueException(e);
        }


    }


    public static class UnformattableValueException extends Exception{
        public UnformattableValueException() {
        }

        public UnformattableValueException(Throwable cause) {
            super(cause);
        }
    }
}
