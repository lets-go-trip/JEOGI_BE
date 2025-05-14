package com.ssafy.tripchat.global.security;

import com.ssafy.tripchat.global.security.domain.LoginRequest;
import com.ssafy.tripchat.global.security.domain.RegisterRequest;
import com.ssafy.tripchat.global.security.domain.UserResponse;
import com.ssafy.tripchat.member.domain.Members;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//AuthController.java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getId(),
                            loginRequest.getPassword()
                    )
            );

            // 인증 성공 시 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션에 사용자 정보 저장
            Members member = userService.findById(loginRequest.getId());
            session.setAttribute("USER", member);

            return ResponseEntity.ok(Map.of(
                    "message", "로그인 성공",
                    "user", UserResponse.from(member),
                    "sessionId", session.getId()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "아이디 또는 비밀번호가 잘못되었습니다."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            Members member = Members.builder().username(registerRequest.getId())
                    .password(registerRequest.getPassword())
                    .nickname(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .build();

            Members savedMember = userService.registerUser(member);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "회원가입 성공",
                            "user", UserResponse.from(savedMember)
                    ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkLoginStatus(HttpSession session) {
        Members member = (Members) session.getAttribute("USER");

        if (member != null) {
            return ResponseEntity.ok(Map.of(
                    "isLoggedIn", true,
                    "user", UserResponse.from(member)
            ));
        } else {
            return ResponseEntity.ok(Map.of("isLoggedIn", false));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
    }
}