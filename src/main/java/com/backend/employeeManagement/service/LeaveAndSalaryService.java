package com.backend.employeeManagement.service;

import com.backend.employeeManagement.exceptions.LeavesLimitExceededException;
import com.backend.employeeManagement.models.LeaveAndSalary;
import com.backend.employeeManagement.repository.LeaveAndSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class LeaveAndSalaryService {

    public List<LeaveAndSalary> getAll(){
        return leaveAndSalaryRepository.findAll();
    }
    @Autowired
    LeaveAndSalaryRepository leaveAndSalaryRepository;
    public long getSalary(long memberId) {
        LeaveAndSalary leaveAndSalary = leaveAndSalaryRepository.findByMemberId(memberId);
        return leaveAndSalary.getSalary();
    }


    public long getLeaves(long memberId){
        LeaveAndSalary leaveAndSalary=leaveAndSalaryRepository.findByMemberId(memberId);
        return leaveAndSalary.getLeaves();
    }

    public void applyLeaves(long memberId) throws LeavesLimitExceededException{
        LeaveAndSalary leaveAndSalary = leaveAndSalaryRepository.findByMemberId(memberId);
        long leaves = leaveAndSalary.getLeaves();
        if(leaves!=0){
            leaveAndSalary.setLeaves(leaves-1);
            leaveAndSalaryRepository.save(leaveAndSalary);
        }
        else {
            throw new LeavesLimitExceededException("Leaves limit exceeded");
        }
    }

    public void deleteId(long id){
        leaveAndSalaryRepository.deleteById(id);
    }
}
