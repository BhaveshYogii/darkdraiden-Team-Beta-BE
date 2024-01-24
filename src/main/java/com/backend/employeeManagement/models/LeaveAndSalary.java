package com.backend.employeeManagement.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "LeaveAndSalary")
public class LeaveAndSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long record_id;

    private long leaves;

    private long salary;
    @Column(name = "member_Id")
    private long memberId;


}
