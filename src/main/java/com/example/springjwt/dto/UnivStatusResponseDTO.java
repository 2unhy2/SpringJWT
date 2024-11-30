package com.example.springjwt.dto;


import lombok.Getter;
import lombok.Setter;

/*
대학 상태를 응답으로 반환하기 위한 DTO
 */
@Setter
@Getter

public class UnivStatusResponseDTO {
    private String universityEmail; // 인증된 대학 이메일
    private String university;      // 대학 이름
    private Boolean univStatus;     // 대학 재학 여부
}
