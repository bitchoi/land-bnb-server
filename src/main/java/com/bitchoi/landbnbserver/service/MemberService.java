package com.bitchoi.landbnbserver.service;

import com.bitchoi.landbnbserver.dto.RegMemberDto;
import com.bitchoi.landbnbserver.model.Member;
import com.bitchoi.landbnbserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;

    public void create(RegMemberDto regMemberDto){
        var existEmail = memberRepo.findByEmail(regMemberDto.getEmail());
        if(existEmail.isPresent()){
            throw new IllegalArgumentException("already use email");
        }
        Member member = new Member();
        member.setEmail(regMemberDto.getEmail());
        member.setPassword(passwordEncoder.encode(regMemberDto.getPassword()));
        member.setLastName(regMemberDto.getLastName());
        member.setFirstName(regMemberDto.getFirstName());
        member.setBirthDay(regMemberDto.getBirthDay());
        memberRepo.save(member);
    }
}
