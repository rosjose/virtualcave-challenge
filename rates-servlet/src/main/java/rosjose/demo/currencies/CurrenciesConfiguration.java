package rosjose.demo.currencies;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import rosjose.demo.currencies.client.CurrencyClientImpl;
import rosjose.demo.currencies.client.CurrencyClientProperties;

@EnableConfigurationProperties(CurrencyClientProperties.class)
@Configuration
@RequiredArgsConstructor
public class CurrenciesConfiguration {

    private final CurrencyClientProperties properties;

    @Bean
    CurrencyClientImpl currencyClient(RestTemplateBuilder restTemplateBuilder){
        RestTemplate restTemplate = restTemplateBuilder.rootUri(properties.getBaseUrl().toString()).build();
        return new CurrencyClientImpl(restTemplate);
    }

}
