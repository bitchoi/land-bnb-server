package com.bitchoi.landbnbserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegMemberDto {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String birthDay;
}
