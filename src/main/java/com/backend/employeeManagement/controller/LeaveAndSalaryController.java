package com.backend.employeeManagement.controller;

import com.backend.employeeManagement.exceptions.LeavesLimitExceededException;
import com.backend.employeeManagement.models.LeaveAndSalary;
import com.backend.employeeManagement.service.LeaveAndSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class LeaveAndSalaryController {

    @Autowired
    private LeaveAndSalaryService leaveAndSalaryService;

    @GetMapping("/all")
    public List<LeaveAndSalary> getAll(){
        return leaveAndSalaryService.getAll();
    }

    @DeleteMapping("delete/{id}")
    public String deleteId(@PathVariable long id){
        leaveAndSalaryService.deleteId(id);
        return "Success";
    }

    //getSalary
    @GetMapping("/salary/{memberId}")
    public long getSalary(@PathVariable long memberId){
        return leaveAndSalaryService.getSalary(memberId);
    }

    //applyLeave
    @PutMapping("/leaves/{memberId}")
    public ResponseEntity<String> applyLeaves(@PathVariable long memberId){
        try{
            leaveAndSalaryService.applyLeaves(memberId);
            return ResponseEntity.ok("Leave Applied");
        }
        catch (LeavesLimitExceededException leavesLimitExceededException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Leave Limit Exceeded");
        }
    }

    //viewLeaves
    @GetMapping("/leaves/{memberId}")
    public long viewLeaves(@PathVariable long memberId){
        return leaveAndSalaryService.getLeaves(memberId);
    }

}
