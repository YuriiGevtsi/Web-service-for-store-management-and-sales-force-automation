package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo extends JpaRepository<Contract,Long> {
}
