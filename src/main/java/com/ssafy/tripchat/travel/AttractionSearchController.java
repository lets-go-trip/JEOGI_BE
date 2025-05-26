package com.ssafy.tripchat.travel;


import com.ssafy.tripchat.travel.dto.AttractionListResponse;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
import java.util.List;
import java.util.Map;
import com.ssafy.tripchat.travel.dto.*;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.ssafy.tripchat.global.security.domain.RegisterRequest;
import com.ssafy.tripchat.global.security.domain.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class AttractionSearchController {

    private final AttractionService attractionService;

    @GetMapping("/condition")
    public ResponseEntity<?> searchByCondition(@Valid @ModelAttribute AttractionSearchCondition searchCondition) {
        AttractionListResponse result = attractionService.searchByCondition(searchCondition);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
    @GetMapping("/local")
    public ResponseEntity<?> fetchLocalList(@RequestParam Integer metropolitanCode){
        LocalListResponse result = attractionService.findAllLocals(metropolitanCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contents-type")
    public ResponseEntity<?> fetchContentsTypeList(){
        ContentTypesListResponse result = attractionService.findAllContentsType();
        return ResponseEntity.ok(result);
    }
}
