package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepo extends JpaRepository<Worker,Long> {
}
