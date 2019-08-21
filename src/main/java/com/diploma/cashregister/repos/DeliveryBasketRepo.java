package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.DeliveryBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryBasketRepo extends JpaRepository<DeliveryBasket,Long> {
}
