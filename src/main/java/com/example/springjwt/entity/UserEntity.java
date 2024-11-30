package com.example.springjwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;//사용자 이름
    private String password;//사용자 비밀번호

    private String role;// 사용자 권한

    private String university;        // 대학 이름
    private String universityEmail;   // 대학 이메일
    private Boolean univStatus;       // 대학 재학 상태 (true: 재학 중, false: 재학 아님)

    private String nickname;//사용자 닉네임
}
