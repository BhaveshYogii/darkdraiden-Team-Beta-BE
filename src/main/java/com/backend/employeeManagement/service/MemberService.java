package com.backend.employeeManagement.service;

import com.backend.employeeManagement.exceptions.EmailAlreadyExistsException;
import com.backend.employeeManagement.exceptions.MemberNotFoundException;
import com.backend.employeeManagement.exceptions.PhoneNumberAlreadyExistsException;
import com.backend.employeeManagement.models.LeaveAndSalary;
import com.backend.employeeManagement.models.Member;
import com.backend.employeeManagement.models.MemberUpdateRequest;
import com.backend.employeeManagement.models.Team;
import com.backend.employeeManagement.repository.LeaveAndSalaryRepository;
import com.backend.employeeManagement.repository.MemberRepository;
import com.backend.employeeManagement.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    LeaveAndSalaryRepository leaveAndSalaryRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member getMemberById(long id){
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    public void deleteById(long id){
        memberRepository.deleteById(id);
    }

    public void saveMember(Member member) {

        if(memberRepository.findByEmail(member.getEmail())!=null)
        {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number is already registered");
        }

//        Team team = teamRepository.findByProfile(member.getProfile()) ;

          member.setManager_id(0);
          member.setTeam_id(0);
          member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

          memberRepository.save(member);
            long  id = member.getMemberId();
        LeaveAndSalary leaveAndSalary = LeaveAndSalary.builder()
                .leaves(10)
                .salary(50000)
                .memberId(id)
                .build();

        leaveAndSalaryRepository.save(leaveAndSalary);


    }
    //update
    public Member updateMemberDetails(long memberId, MemberUpdateRequest updateRequest) {
        Member member1 = memberRepository.findByMemberId(memberId);
        Member member=memberRepository.findByPhoneNumber(updateRequest.getPhoneNumber());
        if(member==null || member.getMemberId()==memberId){
            member1.setProfile(updateRequest.getProfile());
            member1.setAddress(updateRequest.getAddress());
            member1.setPhoneNumber(updateRequest.getPhoneNumber());
            memberRepository.save(member1);
            return member1;
        }
        else{
            throw new PhoneNumberAlreadyExistsException("Phone number already present");
        }

//        member.setEmail(updateRequest.getEmail());

    }

    public boolean validateLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email);

//        String encrypted_password = bCryptPasswordEncoder.encode(password);
        if(member!=null){
            return bCryptPasswordEncoder.matches(password,member.getPassword());
        }
        return false;
    }

    public Member find(String email){
        return memberRepository.findByEmail(email);
    }

    public void resetAutoIncrement() {
        memberRepository.resetAutoIncrement();
    }

    public String getManagerName(String profile){
        Team team = teamRepository.findByProfile(profile);
        return team.getTeam_manager();
    }

    public Member addManager(Member member) {

        if(memberRepository.findByEmail(member.getEmail())!=null)
        {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number is already registered");
        }


        Member member1 = memberRepository.save(member);
        member1.setManager_id(member.getMemberId());
        member1.setPassword(bCryptPasswordEncoder.encode(member1.getPassword()));
        memberRepository.save(member1);
        long  id = member.getMemberId();
        LeaveAndSalary leaveAndSalary = LeaveAndSalary.builder()
                .leaves(10)
                .salary(50000)
                .memberId(id)
                .build();

        leaveAndSalaryRepository.save(leaveAndSalary);
        Team team =  Team.builder().team_manager(member1.getMemberName())
                .profile(member1.getProfile())
                .manager_id((int) member1.getManager_id()).team_name("team" + member1.getMemberId()).build();

        teamRepository.save(team);
        member1.setTeam_id(team.getTeam_id());
        memberRepository.save(member1);


        return member1;
    }

    public Member getByEmail(String email){
        return memberRepository.findByEmail(email);
    }


}
