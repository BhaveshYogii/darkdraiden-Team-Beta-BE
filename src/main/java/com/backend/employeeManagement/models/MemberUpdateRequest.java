package com.backend.employeeManagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MemberUpdateRequest {
    private String memberName;
    private String address;
    private String phoneNumber;
    private String profile;
    private String email;
}
