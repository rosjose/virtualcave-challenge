package rosjose.demo.rates.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rosjose.demo.rates.dao.Rate;
import rosjose.demo.rates.dao.RatesRepository;
import rosjose.demo.rates.dto.ProductRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatesServiceTest {

    @Mock
    CurrencyService currencyService;

    @Mock
    RatesRepository ratesRepository;

    @InjectMocks
    RatesService testClass;

    @Test
    void getProductRateById() throws CurrencyService.UnformattableValueException {

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2001, 1, 1);
        when(ratesRepository.findById(eq(1L)))
                .thenReturn(Optional.of(
                        new Rate(1L, 2L, 3L, startDate, endDate, 1000, 2, "SOME")
                ));
        String expectedFormattedPrice = "Nice format";
        when(currencyService.formatValue(eq("SOME"), any())).thenReturn(expectedFormattedPrice);


        Optional<ProductRate> optionalProductRate = testClass.getProductRateById(1L);

        assertTrue(optionalProductRate.isPresent());
        ProductRate productRate = optionalProductRate.get();
        assertEquals(1L, productRate.getId());
        assertEquals(2L, productRate.getBrandId());
        assertEquals(3L, productRate.getProductId());
        assertEquals(startDate, productRate.getStartDate());
        assertEquals(endDate, productRate.getEndDate());
        assertEquals(BigDecimal.valueOf(1000, 2), productRate.getPrice());
        assertEquals(expectedFormattedPrice, productRate.getFormattedPrice());


        verify(ratesRepository).findById(any());
        verify(currencyService).formatValue(any(), any());
        verifyNoMoreInteractions(ratesRepository, currencyService);
    }

    @Test
    void getProductRateById_whenCurrencyNotFound() throws CurrencyService.UnformattableValueException {

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2001, 1, 1);
        when(ratesRepository.findById(eq(1L)))
                .thenReturn(Optional.of(
                        new Rate(1L, 2L, 3L, startDate, endDate, 1000, 2, "SOME")
                ));
        when(currencyService.formatValue(eq("SOME"), any())).thenThrow(CurrencyService.UnformattableValueException.class);


        Optional<ProductRate> optionalProductRate = testClass.getProductRateById(1L);

        assertTrue(optionalProductRate.isPresent());
        ProductRate productRate = optionalProductRate.get();
        assertEquals(1L, productRate.getId());
        assertEquals(2L, productRate.getBrandId());
        assertEquals(3L, productRate.getProductId());
        assertEquals(startDate, productRate.getStartDate());
        assertEquals(endDate, productRate.getEndDate());
        assertEquals(BigDecimal.valueOf(1000, 2), productRate.getPrice());
        assertEquals(productRate.getPrice().toString(), productRate.getFormattedPrice());


        verify(ratesRepository).findById(any());
        verify(currencyService).formatValue(any(), any());
        verifyNoMoreInteractions(ratesRepository, currencyService);
    }
}