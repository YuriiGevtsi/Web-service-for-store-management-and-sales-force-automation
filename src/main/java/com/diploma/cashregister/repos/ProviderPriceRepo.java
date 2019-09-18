package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProviderPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderPriceRepo extends JpaRepository<ProviderPrice,Long> {
}
