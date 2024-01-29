package com.backend.employeeManagement.repository;

import com.backend.employeeManagement.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);
    Member findByPhoneNumber(String phoneNumber);

//    Optional<Member> findByPhoneNumber(String phoneNumber);

    Member findByMemberId(long memberId);
    @Modifying
    @Query(value = "ALTER TABLE Member AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
