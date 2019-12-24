package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Contract;
import com.diploma.cashregister.domain.Position;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.repos.ContractRepo;
import com.diploma.cashregister.repos.PositionRepo;
import com.diploma.cashregister.repos.WorkerPasswordRepo;
import com.diploma.cashregister.repos.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private final WorkerRepo workerRepo;
    @Autowired
    private final WorkerPasswordRepo workerPasswordRepo;
    @Autowired
    private final ContractRepo contractRepo;
    @Autowired
    private final PositionRepo positionRepo;

    public EmployeeService(WorkerRepo workerRepo, WorkerPasswordRepo workerPasswordRepo, ContractRepo contractRepo, PositionRepo positionRepo) {
        this.workerRepo = workerRepo;
        this.workerPasswordRepo = workerPasswordRepo;
        this.contractRepo = contractRepo;
        this.positionRepo = positionRepo;
    }

    public void createEmployee(Worker worker, WorkerPassword password, Contract contract, Long position){
        workerRepo.save(worker);
        workerPasswordRepo.save(password);
        contract.setPosition(positionRepo.findById(position).get());
        contractRepo.save(contract);
    }

    public List<Position> getAllPositions(){
        return positionRepo.findAll();
    }
}
