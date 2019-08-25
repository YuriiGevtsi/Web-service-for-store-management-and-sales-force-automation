package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.repos.WorkerPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WorkerPasswordService implements UserDetailsService {

    @Autowired
    private final WorkerPasswordRepo workerPasswordRepo;
/*
    @Autowired
    private PasswordEncoder passwordEncoder;
*/
    public WorkerPasswordService(WorkerPasswordRepo workerPasswordRepo) {
        this.workerPasswordRepo = workerPasswordRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return workerPasswordRepo.findById_password(Integer.parseInt(id));
    }
}
