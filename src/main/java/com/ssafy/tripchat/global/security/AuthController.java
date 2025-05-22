package com.ssafy.tripchat.global.security;

import com.ssafy.tripchat.global.security.domain.LoginRequest;
import com.ssafy.tripchat.global.security.domain.RegisterRequest;
import com.ssafy.tripchat.global.security.domain.UserResponse;
import com.ssafy.tripchat.member.domain.Members;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Operation(summary = "로그인", description = "사용자명과 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 실제 구현은 Filter에서 처리됨
        return ResponseEntity.ok("로그인 성공");
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