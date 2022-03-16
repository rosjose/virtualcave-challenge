package rosjose.demo.currencies.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CurrencyClientImpl implements CurrencyClient {

    private static final String API_VERSION = "v1";
    private final RestTemplate restTemplate;


    @Override
    public List<Currency> getCurrencies() {
        ResponseEntity<List<Currency>> exchange = restTemplate.exchange(buildUri("/currencies"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Currency>>() {});

        return exchange.getBody();
    }

    @Override
    public Optional<Currency> getCurrency(String code) throws UnexpectedCurrencyClientException {
        try {
            ResponseEntity<Currency> exchange = restTemplate.exchange(buildUri("/currencies/"+code), HttpMethod.GET, null, Currency.class);
            return Optional.ofNullable(exchange.getBody());
        }catch (HttpStatusCodeException e){
            throw new UnexpectedCurrencyClientException(e);
        }
    }

    private String buildUri(String path){
        return "/"+API_VERSION+ path;
    }
}
