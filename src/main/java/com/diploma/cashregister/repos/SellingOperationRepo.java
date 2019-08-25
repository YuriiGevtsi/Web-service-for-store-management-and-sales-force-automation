package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.SellingOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingOperationRepo  extends JpaRepository<SellingOperation,Long> {
}
