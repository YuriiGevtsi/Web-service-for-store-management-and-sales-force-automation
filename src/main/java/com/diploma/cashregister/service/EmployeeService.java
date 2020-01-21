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
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(WorkerRepo workerRepo, WorkerPasswordRepo workerPasswordRepo, ContractRepo contractRepo, PositionRepo positionRepo, ProviderRepo providerRepo, PasswordEncoder passwordEncoder) {
        this.workerRepo = workerRepo;
        this.workerPasswordRepo = workerPasswordRepo;
        this.contractRepo = contractRepo;
        this.positionRepo = positionRepo;
        this.providerRepo = providerRepo;
        this.passwordEncoder = passwordEncoder;
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

    public void createEmployee(Worker worker, WorkerPassword password, Contract contract, Long position, String login, List<String> roles, String firstName, String lastName, String birth, String pass1, String contact, String start, String finish){

        createWorker(roles, firstName, lastName, birth, contact, worker);

        createPassword(login, pass1, worker, password);

        createContract(start, finish, worker, contract);

        worker.setPosition(positionRepo.findById(position).get());
        workerRepo.save(worker);
        workerPasswordRepo.save(password);
        contract.setPosition(positionRepo.findById(position).get());
        contractRepo.save(contract);
    }

    private void createContract( String start,  String finish, Worker worker, Contract contract) {
        contract.setDateStart(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setDateEnd(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setWorker(worker);
    }

    private void createPassword( String login,  String pass1, Worker worker, WorkerPassword password) {
        password.setPassword(passwordEncoder.encode(pass1));
        password.setLogin(login);
        password.setWorker(worker);
    }

    private void createWorker( List<String> roles,  String firstName,  String lastName,  String birth,  String contact, Worker worker) {
        worker.setDateOfBirthday(LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        worker.setName(firstName);
        worker.setSurname(lastName);
        worker.setContact(contact);
        worker.setRoles(new HashSet<>());
        roles.forEach(role-> worker.addRole(Role.valueOf(role)));
    }
}
