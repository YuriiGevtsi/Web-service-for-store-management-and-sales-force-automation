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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        worker.setPosition(positionRepo.findById(position).get());
        workerRepo.save(worker);
        workerPasswordRepo.save(password);
        contract.setPosition(positionRepo.findById(position).get());
        contractRepo.save(contract);
    }

    public List<Position> getAllPositions(){
        return positionRepo.findAll();
    }

    public List<Worker> getAllEmployees() {
        return workerRepo.findAll();
    }

    public Worker getEmployee(long idWorker) {
        return workerRepo.findById(idWorker).get();
    }

    public WorkerPassword getEmployeesPassword(Worker worker) {
        return workerPasswordRepo.findByWorker(worker);
    }

    public Contract getCurrentContruct(Worker worker) {
        Optional<Contract> contract = worker.getContracts().stream().filter(el ->
                (LocalDate.now().isAfter(el.getDateStart()) || LocalDate.now().isEqual(el.getDateStart()))
                        && (LocalDate.now().isBefore(el.getDateEnd()) || LocalDate.now().isEqual(el.getDateEnd()))).findAny();
        return contract.isPresent() ? contract.get() : new Contract();
    }

    public void deleteEmployee(Long id) {
        workerRepo.delete(workerRepo.findById(id).get());
    }
}
