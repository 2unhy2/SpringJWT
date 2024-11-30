package com.example.springjwt.service;

import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMailService {
    private final UserRepository userRepository;

    @Autowired
    public UserMailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUniv(String username, String univName) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        user.setUniversity(univName);
        userRepository.save(user);
    }

    // 인증 상태 업데이트
    @Transactional
    public void updateUnivVerify(String username) {
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        user.setUnivStatus(true);  // 인증 성공 시 상태를 true로 설정
        userRepository.save(user);
    }
}
