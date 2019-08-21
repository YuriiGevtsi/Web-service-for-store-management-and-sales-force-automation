package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.OrderPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderPaymentsRepo extends JpaRepository<OrderPayments,Long> {

    @Query("from OrderPayments where orders = ?1")
    Set<OrderPayments> findWhereOrders(Long number);
}
