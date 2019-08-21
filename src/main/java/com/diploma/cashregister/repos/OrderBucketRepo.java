package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.OrderBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderBucketRepo extends JpaRepository<OrderBucket,Long> {

    @Query("from OrderBucket where orders = ?1")
    Set<OrderBucket> findAllByOrder(long id);
}
