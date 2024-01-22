package com.backend.employeeManagement.repository;

import com.backend.employeeManagement.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
    Member findByPhoneNumber(String phoneNumber);


}
