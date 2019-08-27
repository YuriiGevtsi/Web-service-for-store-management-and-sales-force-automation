package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeRepo extends JpaRepository<Barcode,Long> {
    @Query("from Barcode where code = ?1")
    Barcode findBarcode(String code);
}
