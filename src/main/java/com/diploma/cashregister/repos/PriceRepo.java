package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Price;
import com.diploma.cashregister.domain.ProviderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PriceRepo extends JpaRepository<Price,Long> {
    @Query("from Price where providerProduct = ?1 and  ?2 BETWEEN dateStart AND dateFinish")
    Price getActualPrice(ProviderProduct providerProduct, LocalDate now);
}
