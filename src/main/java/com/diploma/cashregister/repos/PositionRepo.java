package com.diploma.cashregister.repos;

import com.diploma.cashregister.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepo extends JpaRepository<Position,Long> {
}
