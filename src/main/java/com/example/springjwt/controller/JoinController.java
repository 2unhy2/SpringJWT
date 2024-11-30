package com.example.springjwt.controller;

import com.example.springjwt.dto.JoinDTO;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

//@Controller
//@ResponseBody

@RestController
@RequestMapping("/join")
public class JoinController {

    private final JoinService joinService;
    private final UserRepository userRepository;

    public JoinController(JoinService joinService, UserRepository userRepository) {
        this.joinService = joinService;
        this.userRepository = userRepository;
    }

    // 1. 회원가입 요청 (인증 코드 발송)
    @PostMapping("/request")
    public ResponseEntity<String> requestJoin(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.requestJoin(joinDTO);
            return ResponseEntity.ok("인증 코드가 이메일로 발송되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 2. 인증 코드 확인 및 회원가입 완료
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCodeAndJoin(@RequestBody JoinDTO joinDTO) {

        try {
            joinService.verifyCodeAndJoin(joinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clear(@RequestBody JoinDTO joinDTO) {
        joinService.clear(joinDTO);
        try{
            return ResponseEntity.ok("인증 메일이 초기화 되었습니다.");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
