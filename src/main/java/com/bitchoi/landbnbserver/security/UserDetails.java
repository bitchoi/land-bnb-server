package com.bitchoi.landbnbserver.security;


import com.bitchoi.landbnbserver.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private int userId;

    private String email;

    private String username;

    private String password;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetails(Member member){
        this.email = member.getEmail();
        this.userId = member.getMemberId();
        this.isEnabled = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isAccountNonExpired = true;
    }
}
