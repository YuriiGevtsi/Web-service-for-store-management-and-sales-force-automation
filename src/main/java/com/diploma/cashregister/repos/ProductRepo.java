package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProviderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProviderProduct,Long> {


}
