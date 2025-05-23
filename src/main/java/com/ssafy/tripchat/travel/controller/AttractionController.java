package com.ssafy.tripchat.travel.controller;

import com.ssafy.tripchat.travel.AttractionService;
import com.ssafy.tripchat.travel.dto.AttractionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/api/attractions/{id}")
    public ResponseEntity<AttractionResponse> getAttraction(@PathVariable Integer id) {
        log.info("AttractionController - getAttraction");
        return ResponseEntity.ok(attractionService.getAttractionInfo(id));
    }
}
