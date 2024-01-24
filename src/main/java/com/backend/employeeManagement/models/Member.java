package com.backend.employeeManagement.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long member_Id;

    private String memberName;

    @Column(unique = true)
    private String email;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    private Date dob;

    private String profile;

    private String password;

    @Column(columnDefinition = "bit default 0")
    private boolean checkField;

    private long manager_id;

    private int team_id;


}
