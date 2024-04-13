package com.ryan.simpleBlog.controller;

import com.ryan.simpleBlog.dto.AddUserRequest;
import com.ryan.simpleBlog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request); // 회원가입 메소드 호출
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

    /**
     *
     * 로그아웃 요청을 하면, 로그아웃을 담당하는 핸들러인 SecurityContextLogoutHandler의 logout() 호출
     */
    @GetMapping("/loguout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
