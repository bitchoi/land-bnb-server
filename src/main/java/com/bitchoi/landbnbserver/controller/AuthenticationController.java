package com.bitchoi.landbnbserver.controller;

import com.bitchoi.landbnbserver.dto.JwtRequest;
import com.bitchoi.landbnbserver.dto.JwtResponse;
import com.bitchoi.landbnbserver.facade.AuthenticationFacade;
import com.bitchoi.landbnbserver.service.AuthenticationService;
import com.bitchoi.landbnbserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final MemberService memberService;
    private final AuthenticationService authService;
    private final AuthenticationFacade authenticationFacade;

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

    @GetMapping("/logout")
    void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie tokenCookie = new Cookie("refreshToken", null);
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
        var memberId = authenticationFacade.getAuthentication().getUserId();
        authService.logout(memberId);
    }

}
