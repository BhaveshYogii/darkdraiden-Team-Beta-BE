package com.backend.employeeManagement.repository;

import com.backend.employeeManagement.models.LeaveAndSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveAndSalaryRepository extends JpaRepository<LeaveAndSalary,Long> {
    LeaveAndSalary findByMemberId(long memberId);
}
