package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends JpaRepository<ProductCategory,Long> {
}
