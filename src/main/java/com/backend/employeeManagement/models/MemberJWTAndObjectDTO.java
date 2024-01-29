package com.backend.employeeManagement.models;

import com.backend.employeeManagement.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJWTAndObjectDTO {
    private String token;
    private Member member;
}
