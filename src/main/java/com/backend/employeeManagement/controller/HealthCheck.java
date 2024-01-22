package com.backend.employeeManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("health-check")
    public String check(){
        return "Application Working Fine";
    }

}
