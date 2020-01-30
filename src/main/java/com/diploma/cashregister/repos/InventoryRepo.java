package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Inventory;
import com.diploma.cashregister.domain.WrittenOffProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
}
