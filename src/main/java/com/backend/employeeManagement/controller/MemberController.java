package com.backend.employeeManagement.controller;

import com.backend.employeeManagement.exceptions.EmailAlreadyExistsException;
import com.backend.employeeManagement.exceptions.PhoneNumberAlreadyExistsException;
import com.backend.employeeManagement.models.Member;
import com.backend.employeeManagement.models.MemberJWTAndObjectDTO;
import com.backend.employeeManagement.models.MemberLoginRequest;
import com.backend.employeeManagement.models.MemberUpdateRequest;
import com.backend.employeeManagement.service.JwtService;
import com.backend.employeeManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:3002"})
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;


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
    public ResponseEntity<MemberJWTAndObjectDTO> login(@RequestBody MemberLoginRequest loginRequest) {
        Member member = null;
        String token = null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            member = memberService.getByEmail(loginRequest.getEmail());
            token = jwtService.GenerateToken(loginRequest.getEmail());
            MemberJWTAndObjectDTO memberJWTAndObjectDTO = new MemberJWTAndObjectDTO(token,member);
            return ResponseEntity.ok(memberJWTAndObjectDTO);
        }
        else{
            MemberJWTAndObjectDTO memberJWTAndObjectDTO = new MemberJWTAndObjectDTO(token,member);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(memberJWTAndObjectDTO);
        }
    }


    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<Member> updateMemberDetails(@RequestBody MemberUpdateRequest updateRequest,@PathVariable long id ) {
        try {
            // Call MemberService to update member details
            Member member = memberService.updateMemberDetails(id,updateRequest);

            return ResponseEntity.ok(member);
        } catch (PhoneNumberAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        memberService.resetAutoIncrement();
        return ResponseEntity.ok("Auto-increment sequence reset successfully.");
    }

    @GetMapping("/manager-name/{profile}")
    public ResponseEntity<String> getManagerName(@PathVariable String profile){
        String manager_name = memberService.getManagerName(profile);
        return ResponseEntity.ok(manager_name);
    }

    @PostMapping("/add-manager")
    public ResponseEntity<String> addManager(@RequestBody Member member) {

        try {

            Member member1 = memberService.addManager(member);

            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        } catch (PhoneNumberAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already registered");
        }
    }


}
