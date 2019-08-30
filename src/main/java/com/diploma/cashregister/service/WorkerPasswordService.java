package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.ShiftWorker;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.repos.ShiftWorkerRepo;
import com.diploma.cashregister.repos.WorkerPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkerPasswordService implements UserDetailsService {

    @Autowired
    private final WorkerPasswordRepo workerPasswordRepo;
    @Autowired
    private final ShiftWorkerRepo shiftWorkerRepo;
/*
    @Autowired
    private PasswordEncoder passwordEncoder;
*/
    public WorkerPasswordService(WorkerPasswordRepo workerPasswordRepo, ShiftWorkerRepo shiftWorkerRepo) {
        this.workerPasswordRepo = workerPasswordRepo;
        this.shiftWorkerRepo = shiftWorkerRepo;
    }

    public void logOutWorker(Worker worker){
        ShiftWorker currentShift = shiftWorkerRepo.findCurrentShift(worker);
        currentShift.setLogoutTime(LocalDateTime.now());
        shiftWorkerRepo.save(currentShift);
    }


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return workerPasswordRepo.findById_password(Integer.parseInt(id));
    }
}
