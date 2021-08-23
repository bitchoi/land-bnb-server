package com.bitchoi.landbnbserver.dto;

import com.bitchoi.landbnbserver.constant.GrantType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

    private GrantType grantType;

    private String refreshToken;
}
