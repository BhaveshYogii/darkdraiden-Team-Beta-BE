package com.backend.employeeManagement.service;

import com.backend.employeeManagement.models.Member;
import com.backend.employeeManagement.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MemberDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    private static final Logger logger =  LoggerFactory.getLogger(MemberDetailsServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       logger.debug("Entering in loadUserByUsername Method.");
        Member member = memberRepository.findByEmail(username);
        if(member==null){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomMemberDetails(member);

    }
}
