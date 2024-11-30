package com.example.springjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDTO {// MyPage에서 필요한 것들
    private String nickname;//닉네임
    private String username;       // 사용자 이름
    private String universityName; // 대학 이름
    private String universityEmail; // 대학 이메일

}
