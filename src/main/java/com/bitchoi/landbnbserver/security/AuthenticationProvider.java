package com.bitchoi.landbnbserver.security;

import com.bitchoi.landbnbserver.service.MemberService;
import com.bitchoi.landbnbserver.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log4j2
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final MemberService memberService;

    private final JwtUtils jwtUtils;
    
    public AuthenticationProvider(MemberService memberService, JwtUtils jwtUtils) {
        this.memberService = memberService;
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        String jwtToken = authenticationToken.getCredentials().toString();
        if (jwtUtils.isTokenExpired(jwtToken))
            throw new AccountExpiredException("Login token is expired");
        String userEmail = jwtUtils.getUserEmailFromToken(jwtToken);
        if (StringUtils.isEmpty(userEmail))
            throw new UsernameNotFoundException("Login token does not contain user email");

        // Once we get the token validate it.
        log.debug("Querying user info with info from the token");

        var usrDetails =
                memberService.getUserByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User " +
                        "not found with email " + userEmail));

        // if token is valid configure Spring Security to manually set
        // authentication
        log.debug("Found user with details from Bearer token, returning UserDetails");
        return usrDetails;
    }
}
