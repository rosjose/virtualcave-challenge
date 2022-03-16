package rosjose.demo.currencies.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties("services.currencies.client")
@Data
public class CurrencyClientProperties {
    URL baseUrl;
}
