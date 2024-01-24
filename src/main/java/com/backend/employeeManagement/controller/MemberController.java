package com.backend.employeeManagement.controller;

import com.backend.employeeManagement.exceptions.EmailAlreadyExistsException;
import com.backend.employeeManagement.exceptions.MemberNotFoundException;
import com.backend.employeeManagement.exceptions.PhoneNumberAlreadyExistsException;
import com.backend.employeeManagement.models.Member;
import com.backend.employeeManagement.models.MemberLoginRequest;
import com.backend.employeeManagement.models.MemberUpdateRequest;
import com.backend.employeeManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all-member")
    public ResponseEntity<List<Member>> getAllMembers(){
        List<Member> members=memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/member-byId/{myId}")
    public ResponseEntity<Member> getMemberById(@PathVariable long myId){
        Member member = memberService.getMemberById(myId);

        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("/add-member")
    public ResponseEntity<String> createMember(@RequestBody Member member) {

        try{
            memberService.saveMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
        }
        catch(EmailAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        }
        catch (PhoneNumberAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already registered");
        }
    }




    @DeleteMapping("delete-member/{memberId}")
    public void deleteById(@PathVariable long memberId) {
        memberService.deleteById(memberId);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody MemberLoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        boolean loginSuccessful = memberService.validateLogin(email, password);


        if (loginSuccessful) {
            Member member = memberService.find(email);
                 return ResponseEntity.ok(member);
            }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    //update
    @PutMapping("/update")
    public ResponseEntity<String> updateMemberDetails(@RequestBody MemberUpdateRequest updateRequest) {
        try {
            // Assume you have the member's ID available after login
            long memberId = 123; // Replace with actual member ID

            // Call MemberService to update member details
            memberService.updateMemberDetails(memberId, updateRequest);

            return ResponseEntity.ok("Member details updated successfully");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
    }

    @GetMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        memberService.resetAutoIncrement();
        return ResponseEntity.ok("Auto-increment sequence reset successfully.");
    }

}
