package com.bitchoi.landbnbserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class JwtResponse {

    private String accessToken;

    private Date expiredOn;

    @Setter
    private String refreshToken;

    @Builder
    public JwtResponse(String accessToken, Date expiredOn){
        this.accessToken = accessToken;
        this.expiredOn = expiredOn;
    }
}
