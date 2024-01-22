package com.backend.employeeManagement.service;

import com.backend.employeeManagement.exceptions.EmailAlreadyExistsException;
import com.backend.employeeManagement.exceptions.MemberNotFoundException;
import com.backend.employeeManagement.exceptions.PhoneNumberAlreadyExistsException;
import com.backend.employeeManagement.models.Member;
import com.backend.employeeManagement.models.MemberUpdateRequest;
import com.backend.employeeManagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

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

    public void saveMember(Member member, boolean isManager) {

        if(memberRepository.findByEmail(member.getEmail())!=null)
        {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        if (memberRepository.findByPhoneNumber(member.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number is already registered");
        }
        member.setCheckField(isManager);
        memberRepository.save(member);
    }
    //update
    public void updateMemberDetails(long memberId, MemberUpdateRequest updateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));


        member.setMemberName(updateRequest.getMemberName());
        member.setAddress(updateRequest.getAddress());
        member.setPhoneNumber(updateRequest.getPhoneNumber());
        member.setProfile(updateRequest.getProfile());


        memberRepository.save(member);
    }

    public boolean validateLogin(String email, String password) {
        Member member = memberRepository.findByEmail(email);


        return member != null && member.getPassword().equals(password);
    }

    public Member find(String email){
        return memberRepository.findByEmail(email);
    }

}
