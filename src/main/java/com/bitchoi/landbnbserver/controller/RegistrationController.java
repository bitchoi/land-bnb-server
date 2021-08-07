package com.bitchoi.landbnbserver.controller;

import com.bitchoi.landbnbserver.dto.RegMemberDto;
import com.bitchoi.landbnbserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final MemberService userService;

    @PostMapping
    void signUp(@RequestBody RegMemberDto regMemberDto){
        userService.create(regMemberDto);
    }
}
