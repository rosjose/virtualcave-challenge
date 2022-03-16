package rosjose.demo.rates.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity class to access T_RATES table
 * <pre>
 * * **ID**: Identificador único de la tarifa
 * * **BRAND_ID**: Identificador único de la marca
 * * **PRODUCT_ID**: Identificador único del producto
 * * **START_DATE**: Fecha de aplicación de la tarifa
 * * **END_DATE**: Fecha de fin de aplicación de la tarifa
 * * **PRICE**: Precio del producto sin decimales, los decimales deben extraerse del servicio de moneda
 * * **CURRENCY_CODE**: ID de la moneda en que está representado el precio
 * </pre>
 */
@Entity
@Table(name = "T_RATES")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Rate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long brandId;

    private Long productId;

    private LocalDate startDate;

    private LocalDate endDate;

    private int price;

    private int priceScale;

    private String currencyCode;
}
