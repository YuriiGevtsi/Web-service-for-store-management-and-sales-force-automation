package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.repos.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private final WorkerRepo workerRepo;

    public EmployeeService(WorkerRepo workerRepo) {
        this.workerRepo = workerRepo;
    }

    public void createEmployee(Worker worker){
        workerRepo.save(worker);
    }
}
