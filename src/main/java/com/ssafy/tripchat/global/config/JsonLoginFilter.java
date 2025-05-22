package com.ssafy.tripchat.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripchat.global.security.domain.LoginRequest;
import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import com.ssafy.tripchat.global.security.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
        setAuthenticationSuccessHandler(this::success);
        setAuthenticationFailureHandler(this::failure);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        try {
            // TODO : global Exception을 정의하면 수정
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException(
                        "Authentication method not supported: " + request.getMethod());
            }

            LoginRequest dto = objectMapper.readValue(request.getInputStream(),
                    LoginRequest.class);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());

            return this.getAuthenticationManager().authenticate(token); // Provider 위임
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void success(HttpServletRequest req, HttpServletResponse res,
                         Authentication auth) throws IOException {
        MemberPrincipal member = ((MemberPrincipal) auth.getPrincipal());

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.OK.value());
        LoginResponse response = LoginResponse.success(member);
        objectMapper.writeValue(res.getWriter(), response);
    }

    private void failure(HttpServletRequest req, HttpServletResponse res,
                         AuthenticationException ex) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        LoginResponse response = LoginResponse.failure();

        objectMapper.writeValue(res.getWriter(), response);
    }
}

