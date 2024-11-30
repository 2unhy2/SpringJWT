package com.example.springjwt.controller;

import com.example.springjwt.dto.MyPageDTO;
import com.example.springjwt.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/MyPage")
public class MyPageApiController {
    private final MyPageService myPageService;

    public MyPageApiController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }
//json형식이랑 html형식이랑 헷갈리게 해놔서 바꾼거임
    @GetMapping
    public ResponseEntity<MyPageDTO> getMyPage(Authentication authentication) {
        String username = authentication.getName(); // 인증된 사용자 정보
        MyPageDTO myPageDTO = myPageService.getMyPage(username);
        return ResponseEntity.ok(myPageDTO); // JSON 응답
    }
}
