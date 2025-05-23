package com.ssafy.tripchat.travel;

import com.ssafy.tripchat.travel.dto.AttractionListResponse;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
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

}
