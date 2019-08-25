package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery,Long> {
}
