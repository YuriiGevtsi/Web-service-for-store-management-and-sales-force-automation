package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.repos.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WorkerService implements UserDetailsService {
    @Autowired
    private final WorkerRepo workerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public WorkerService(WorkerRepo workerRepo) {
        this.workerRepo = workerRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Worker worker = workerRepo.findByName(username);
        if (worker == null) throw new UsernameNotFoundException("Worker not found");
        return worker;
    }
}
