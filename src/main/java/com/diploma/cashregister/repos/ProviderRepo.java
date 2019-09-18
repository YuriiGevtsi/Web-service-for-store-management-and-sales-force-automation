package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepo extends JpaRepository<Provider,Long> {
}
