package rosjose.demo.rates.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rosjose.demo.rates.dto.ProductRate;
import rosjose.demo.rates.dto.ProductRatePrice;
import rosjose.demo.rates.dao.Rate;
import rosjose.demo.rates.dao.RatesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesService {

    private final CurrencyService currencyService;
    private final RatesRepository ratesRepository;

    public Rate createRate(Rate rate) {
        //Ensure no id is taken into account
        rate.setId(null);
        return ratesRepository.save(rate);
    }

    public void deleteRate(Long id){
        try {
            ratesRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            log.warn(e.getMessage());
        }
    }

    @Transactional
    public Optional<Rate> updateProductRatePrice(Long id, ProductRatePrice productRatePrice){
        int i = ratesRepository.updatePrice(id, productRatePrice.getPrice(), productRatePrice.getPriceScale());
        if (i > 0){
            return ratesRepository.findById(id);
        }
        return Optional.empty();
    }


    public Optional<ProductRate> getProductRateById(Long id){
        return ratesRepository.findById(id).map(this::rateToProductRate);
    }

    public Optional<ProductRate> getProductRateByDateProductBrand(LocalDate date, Long brandId, Long productId){
        return StreamSupport.stream(ratesRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date).spliterator(), false)
                .reduce( (rateA, rateB) ->
                    //In case more than one rate matches, get the one with the closest startDate
                    (rateA.getStartDate().compareTo(rateB.getStartDate())>=0)?rateA:rateB
                ).map(this::rateToProductRate);
    }

    public Stream<ProductRate> getProductRates(){
        return StreamSupport.stream(ratesRepository.findAll().spliterator(), false).map(this::rateToProductRate);
    }

    private ProductRate rateToProductRate(Rate rate) {
        BigDecimal price = BigDecimal.valueOf(rate.getPrice(), rate.getPriceScale());
        String formattedPrice;
        try {
            formattedPrice = currencyService.formatValue(rate.getCurrencyCode(), price);
        }catch (CurrencyService.UnformattableValueException e){
            log.warn("Unable to format price for rate with id {}", rate.getId(), e);
            formattedPrice = price.toString();
        }
        return ProductRate.builder()
                .id(rate.getId())
                .productId(rate.getProductId())
                .brandId(rate.getBrandId())
                .startDate(rate.getStartDate())
                .endDate(rate.getEndDate())
                .price(price)
                .formattedPrice(formattedPrice)
                .currencyCode(rate.getCurrencyCode())
                .build();
    }
}
