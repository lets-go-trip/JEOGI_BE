package com.ssafy.tripchat.travel;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripchat.global.security.domain.RegisterRequest;
import com.ssafy.tripchat.global.security.domain.UserResponse;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class AttractionSearchController {

    private final AttractionService attractionService;

    @GetMapping("/condition")
    public ResponseEntity<?> searchByCondition(@Valid @RequestBody AttractionSearchCondition searchCondition) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of(
                        "message", "검색 성공",
                        "data", attractionService.searchByCondition(searchCondition)
                ));
    }
}
