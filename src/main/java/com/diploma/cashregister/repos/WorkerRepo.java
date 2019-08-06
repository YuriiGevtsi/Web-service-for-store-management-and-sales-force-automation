package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepo extends JpaRepository<Worker,Long> {
    Worker findByName(String name);

}
