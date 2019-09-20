package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.ShiftWorker;
import com.diploma.cashregister.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShiftWorkerRepo extends JpaRepository<ShiftWorker,Integer> {

    @Query("from ShiftWorker where worker = ?1 and logoutTime is null")
    Optional<ShiftWorker> findCurrentShift(Worker worker);
}
