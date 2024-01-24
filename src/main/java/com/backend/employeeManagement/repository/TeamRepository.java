package com.backend.employeeManagement.repository;

import com.backend.employeeManagement.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team,Integer> {
    Team findByProfile(String profile);
    @Modifying
    @Query(value = "ALTER TABLE Team AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
