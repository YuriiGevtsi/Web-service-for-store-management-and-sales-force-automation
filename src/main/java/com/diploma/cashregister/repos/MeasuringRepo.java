package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProviderProductMeasuringRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuringRepo extends JpaRepository<ProviderProductMeasuringRate,Long> {
}
