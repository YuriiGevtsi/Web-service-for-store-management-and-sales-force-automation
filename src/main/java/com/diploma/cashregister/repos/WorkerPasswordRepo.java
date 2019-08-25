package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.WorkerPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerPasswordRepo extends JpaRepository<WorkerPassword,Long> {

    @Query("from WorkerPassword where idPassword = ?1")
    WorkerPassword findById_password(int id);


}
