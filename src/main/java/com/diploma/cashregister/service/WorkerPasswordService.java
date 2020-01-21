package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Shift;
import com.diploma.cashregister.domain.ShiftWorker;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.repos.ShiftRepo;
import com.diploma.cashregister.repos.ShiftWorkerRepo;
import com.diploma.cashregister.repos.WorkerPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class WorkerPasswordService implements UserDetailsService {

    @Autowired
    private final WorkerPasswordRepo workerPasswordRepo;
    @Autowired
    private final ShiftWorkerRepo shiftWorkerRepo;
    @Autowired
    private final ShiftRepo shiftRepo;

    public WorkerPasswordService(WorkerPasswordRepo workerPasswordRepo, ShiftWorkerRepo shiftWorkerRepo, ShiftRepo shiftRepo) {
        this.workerPasswordRepo = workerPasswordRepo;
        this.shiftWorkerRepo = shiftWorkerRepo;
        this.shiftRepo = shiftRepo;
    }

    public void logOutWorker(Worker worker){
        ShiftWorker currentShift = shiftWorkerRepo.findCurrentShift(worker).get();
        currentShift.setLogoutTime(LocalDateTime.now());
        shiftWorkerRepo.save(currentShift);
    }


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return workerPasswordRepo.findByLogin(id);
    }

    public void createShift(Worker worker) {
        Shift shift = new Shift();
        shift.setBeginningTime(LocalDateTime.now());
        shiftRepo.save(shift);
        createShiftWorker(worker,shift);
    }

    public void createShiftWorker(Worker worker, Shift shift){
        ShiftWorker shiftWorker = new ShiftWorker();
        shiftWorker.setLoginTime(LocalDateTime.now());
        shiftWorker.setWorker(worker);
        if (shift!=null) {
            shiftWorker.setShift(shift);
            shiftWorkerRepo.save(shiftWorker);

            shift.setShiftWorkers(shiftWorker);
            shiftRepo.save(shift);
        }else shiftWorkerRepo.save(shiftWorker);
    }

    public Shift getCurrentShift() {
        return shiftRepo.getCurrentShift();
    }

    public Optional<ShiftWorker> getCurrentShiftWorker(Worker worker) {
        return shiftWorkerRepo.findCurrentShift(worker);
    }

    public void closeShift(Worker worker) {
        logOutWorker(worker);
        Shift shift = shiftRepo.getCurrentShift();
        shift.setEndingTime(LocalDateTime.now());
        shiftRepo.save(shift);
    }
}
