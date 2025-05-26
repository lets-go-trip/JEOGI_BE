package com.ssafy.tripchat.global.config;

import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        if (principal instanceof MemberPrincipal) {
            MemberPrincipal memberPrincipal = (MemberPrincipal) principal;

            // 리플렉션 또는 Setter로 password 제거
            try {
                Field passwordField = MemberPrincipal.class.getDeclaredField("password");
                passwordField.setAccessible(true);
                passwordField.set(memberPrincipal, null);
                
            } catch (Exception e) {
                throw new RuntimeException("Failed to nullify password", e);
            }
        }

        log.info("Login successful for user: {}", authentication.getName());

        response.setStatus(HttpServletResponse.SC_OK);
    }

}
