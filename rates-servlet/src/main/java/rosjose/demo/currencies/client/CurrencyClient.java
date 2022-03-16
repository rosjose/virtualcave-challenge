package rosjose.demo.currencies.client;

import java.util.List;
import java.util.Optional;

public interface CurrencyClient {

    List<Currency> getCurrencies();

    Optional<Currency> getCurrency(String code) throws UnexpectedCurrencyClientException;

}
