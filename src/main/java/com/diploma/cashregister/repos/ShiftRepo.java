package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepo extends JpaRepository<Shift,Long> {
    @Query("from Shift where endingTime is null")
    Shift getCurrentShift();
}
