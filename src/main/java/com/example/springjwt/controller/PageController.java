package com.example.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    @GetMapping("/login-page")
    public String loginPage() {
        return "login"; // templates/login.html 반환
    }

    @GetMapping("/main-page")
    public String mainPage() {
        return "site";//이게 우리 메인페이지
    }

    @GetMapping("/signup-page")
    public String singupPage() {
        return "signup";
    }



}
