package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProductConnectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductConnectCategoryRepo extends JpaRepository<ProductConnectCategory,Long> {
}
