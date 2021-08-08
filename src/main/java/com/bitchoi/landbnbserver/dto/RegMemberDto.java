package com.bitchoi.landbnbserver.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class RegMemberDto {

    @Pattern(regexp = "^.+@.+\\..+$")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String birthDay;
}
