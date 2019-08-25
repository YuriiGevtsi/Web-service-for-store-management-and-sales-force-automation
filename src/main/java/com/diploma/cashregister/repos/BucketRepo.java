package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BucketRepo extends JpaRepository<Bucket,Long> {

    @Query("from Bucket where sellingOperation.idSelling = ?1")
    Set<Bucket> findReceiptProducts(Long number);
}
