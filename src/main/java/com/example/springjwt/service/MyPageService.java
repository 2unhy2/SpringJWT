package com.example.springjwt.service;

import com.example.springjwt.dto.MyPageDTO;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private final UserRepository userRepository;

    public MyPageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // 현재 로그인한 사용자의 닉네임을 반환
    @Transactional
    public MyPageDTO getMyPage(String username) {
        // username을 사용해서 사용자 정보 조회
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        // MyPageDTO 생성 및 사용자 정보 매핑
        MyPageDTO myPageDTO = new MyPageDTO();
        myPageDTO.setNickname(user.getNickname());         // 닉네임
        myPageDTO.setUsername(user.getUsername());         // 사용자 이름
        myPageDTO.setUniversityName(user.getUniversity()); // 대학 이름
        myPageDTO.setUniversityEmail(user.getUniversityEmail()); // 대학 이메일

        return myPageDTO;
    }

@Transactional
    public void updateNickname(String username, String newNickname) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
        throw new RuntimeException("사용자를 찾을 수 없습니다.");
    }

            user.setNickname(newNickname);  // 닉네임 업데이트
            userRepository.save(user);       // DB에 저장

    }

}
