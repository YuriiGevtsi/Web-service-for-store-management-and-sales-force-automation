package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProviderConnectProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderConnectProductRepo extends JpaRepository<ProviderConnectProduct,Long> {
}
