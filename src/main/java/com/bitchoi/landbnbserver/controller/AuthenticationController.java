package com.bitchoi.landbnbserver.controller;

import com.bitchoi.landbnbserver.dto.JwtRequest;
import com.bitchoi.landbnbserver.dto.JwtResponse;
import com.bitchoi.landbnbserver.service.AuthenticationService;
import com.bitchoi.landbnbserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final MemberService memberService;
    private final AuthenticationService authService;

    @PostMapping("/login")
    JwtResponse login(HttpServletResponse response, @RequestBody JwtRequest jwtRequest){
        var res = authService.authenticateWithToken(jwtRequest);
        Cookie cookie = new Cookie("refreshToken", res.getRefreshToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
        return res;
    }

}
