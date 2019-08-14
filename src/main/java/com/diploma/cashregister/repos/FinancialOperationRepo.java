package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.FinancialOperations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialOperationRepo extends JpaRepository<FinancialOperations,Long> {


}
