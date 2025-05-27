package com.ssafy.tripchat.travel;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripchat.travel.dto.AttractionListResponse;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
import com.ssafy.tripchat.travel.dto.ContentTypesListResponse;
import com.ssafy.tripchat.travel.dto.LocalListResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<?> fetchLocalList(@RequestParam(name="metropolitanCode") Integer metropolitanCode){
        LocalListResponse result = attractionService.findAllLocals(metropolitanCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contents-type")
    public ResponseEntity<?> fetchContentsTypeList(){
        ContentTypesListResponse result = attractionService.findAllContentsType();
        return ResponseEntity.ok(result);
    }
}
