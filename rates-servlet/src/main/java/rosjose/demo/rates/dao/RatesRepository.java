package rosjose.demo.rates.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RatesRepository extends CrudRepository<Rate, Long> {

    @Modifying
    @Query("update Rate r set r.price = :price, price_scale= :priceScale where r.id = :id")
    int updatePrice(@Param(value = "id")Long id, @Param(value = "price") int price, @Param(value = "priceScale") int scale);

    @Query("select r from Rate r where r.brandId = :brandId and r.productId = :productId and :date between r.startDate and r.endDate")
    Iterable<Rate> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDate date);
}
