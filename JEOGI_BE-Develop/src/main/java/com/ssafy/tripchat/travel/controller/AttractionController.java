package com.ssafy.tripchat.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripchat.travel.AttractionService;
import com.ssafy.tripchat.travel.dto.AttractionDetailResponse;
import com.ssafy.tripchat.travel.dto.AttractionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/api/attractions/{id}")
    public ResponseEntity<AttractionDetailResponse> getAttraction(@PathVariable(name="id") Integer id) {
        log.info("AttractionController - getAttraction");
        return ResponseEntity.ok(attractionService.getAttractionInfo(id));
    }
}
