package com.example.springjwt.controller;

import com.example.springjwt.dto.MyPageDTO;
import com.example.springjwt.entity.UserEntity;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.service.MyPageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/MyPage")
public class MyPageController {
    private final UserRepository userRepository;
    private final MyPageService myPageService;

    public MyPageController(UserRepository userRepository, MyPageService myPageService) {
        this.userRepository = userRepository;
        this.myPageService = myPageService;


    }

    @GetMapping
    public String myPage() {
        // templates/mypage.html 반환
        return "mypage";
    }

    //사용자 이름 반환




    // 닉네임 수정 요청 처리
    //json 파일 형식 //"nickname": "새로운닉네임"
    @PostMapping("/edit")
    public ResponseEntity<String> updateNickname(@RequestBody Map<String, String> request, Authentication authentication) {
        String username = authentication.getName();
        String newNickname = request.get("nickname");

        // 닉네임 업데이트 호출
        myPageService.updateNickname(username, newNickname);

        // 응답: 닉네임 변경 성공 메시지
        return ResponseEntity.ok("닉네임이 '" + newNickname + "'(으)로 변경되었습니다.");
    }




    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            System.out.println("User logged out: " + username);

            // SecurityContext 초기화
            SecurityContextHolder.clearContext();
            System.out.println("SecurityContext cleared for user: " + username);
        } else {
            System.out.println("No authentication found for logout.");
        }

        // 클라이언트에서 JWT 삭제 요청
        return ResponseEntity.ok("로그아웃되었습니다.");
    }


}
