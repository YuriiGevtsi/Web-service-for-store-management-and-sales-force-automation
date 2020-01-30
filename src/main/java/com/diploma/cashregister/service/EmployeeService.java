package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.*;
import com.diploma.cashregister.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
    @Autowired
    private final ProviderRepo providerRepo;


    public EmployeeService(WorkerRepo workerRepo, WorkerPasswordRepo workerPasswordRepo, ContractRepo contractRepo, PositionRepo positionRepo, ProviderRepo providerRepo) {
        this.workerRepo = workerRepo;
        this.workerPasswordRepo = workerPasswordRepo;
        this.contractRepo = contractRepo;
        this.positionRepo = positionRepo;
        this.providerRepo = providerRepo;
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

    public List<Contract> getAllContracts() {
        return contractRepo.findAll();
    }

    public void deleteContract(Long id) {
        contractRepo.delete(contractRepo.findById(id).get());
    }

    public List<Provider> getAllProviders() {
        return providerRepo.findAll();
    }

    public void createProvider(String name, String email, String address) {
        Provider provider = new Provider();
        provider.setAddress(address);
        provider.setEMail(email);
        provider.setName(name);
        providerRepo.save(provider);
    }

    public void deleteProvider(Long id) {
        Provider provider = providerRepo.findById(id).get();
        providerRepo.delete(provider);
    }

    public void editProvider(String idProvider, String name, String email, String address) {
        Provider provider = providerRepo.findById(Long.valueOf(idProvider)).get();
        provider.setAddress(address);
        provider.setEMail(email);
        provider.setName(name);
        providerRepo.save(provider);
    }


    public void saveEmployee(Worker worker, Contract contract, WorkerPassword workerPassword) {
        workerRepo.save(worker);
        workerPasswordRepo.save(workerPassword);
        contractRepo.save(contract);
    }

    public void saveEmployeesNewContract(Contract contract) {
        contractRepo.save(contract);
    }

    public boolean checkLogin(String login) {
        return workerPasswordRepo.findByLogin(login) == null;
    }
}
