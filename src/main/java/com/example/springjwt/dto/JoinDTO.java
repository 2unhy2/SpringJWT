package com.example.springjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String username;
    private String password;
    private String universityEmail;   // 대학 이메일
    private String universityName;    // 대학 이름
    private int verificationCode;      // 인증 코드 (사용자가 입력)
}
