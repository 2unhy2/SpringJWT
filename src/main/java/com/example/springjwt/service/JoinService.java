package com.example.springjwt.service;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.dto.UnivStatusResponseDTO;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import com.univcert.api.UnivCert;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final UnivStatusResponseService univStatusResponseService;

    private static final String API_KEY = "6aa47fc8-8921-4c14-9b40-253dd93a5daa";

    // 인증 코드를 임시 저장하기 위한 맵
    private Map<String, String> verificationCodes = new HashMap<>();

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    //회원가입

    @Transactional
    public void requestJoin(JoinDTO joinDTO) {
        String universityEmail = joinDTO.getUniversityEmail();
        String universityName = joinDTO.getUniversityName();

        // 대학 인증 요청 (인증 코드 발송)
        Map<String, Object> certifyResponse;
        try {
            certifyResponse = UnivCert.certify(API_KEY, universityEmail, universityName, true);
        } catch (IOException e) {
            throw new RuntimeException("대학 인증 요청 중 오류 발생: " + e.getMessage());
        }

        if (!(Boolean) certifyResponse.get("success")) {
            throw new RuntimeException("대학 인증 실패: " + certifyResponse.get("message"));
        }
    }

    // 2. 인증 코드 확인 및 회원가입 완료
    @Transactional
    public void verifyCodeAndJoin(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        //String password = joinDTO.getPassword();

        String universityEmail = joinDTO.getUniversityEmail();

        int inputCode = joinDTO.getVerificationCode();//내가쓴 코드 저장



        // 인증 코드 확인을 위해 API에 다시 요청하여 코드 확인
        Map<String, Object> codeResponse;
        try {
            codeResponse = UnivCert.certifyCode(API_KEY, universityEmail, joinDTO.getUniversityName(), inputCode);
        } catch (IOException e) {
            throw new RuntimeException("인증 코드 확인 중 오류 발생: " + e.getMessage());
        }

        // 인증 코드 확인 결과를 체크
        if (!(Boolean) codeResponse.get("success")) {
            throw new RuntimeException("인증 코드 확인 실패: " + codeResponse.get("message"));
        }

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {//유저 이름이 존재하면

            return;
        }


        // 인증이 성공하면 사용자 정보를 데이터베이스에 저장
        UserEntity user = new UserEntity();
        user.setUsername(joinDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        user.setRole("ROLE_ADMIN");
        user.setUniversityEmail(universityEmail);
        user.setUniversity(joinDTO.getUniversityName());
        user.setUnivStatus(true); // 인증 상태
        user.setNickname("사용자");

        userRepository.save(user);

        // 사용자가 등록된 후 인증 코드를 삭제
        //verificationCodes.remove(universityEmail);
    }

    @Transactional
    public void clear(JoinDTO joinDTO){
        Map<String, Object> certifyResponse;
        try {
            UnivCert.clear(API_KEY); // 이전 인증 코드 초기화
        } catch (IOException e) {
            throw new RuntimeException("인증 코드 초기화 중 오류 발생: " + e.getMessage());
        }

    }
}
