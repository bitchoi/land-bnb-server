package com.bitchoi.landbnbserver.facade;

import com.bitchoi.landbnbserver.security.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public UserDetails getAuthentication(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
