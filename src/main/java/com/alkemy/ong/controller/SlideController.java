package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.service.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    SlideService slideService;
    @GetMapping
    public ResponseEntity<List<SlideBasicDto>> getAllSlides(){
        return ResponseEntity.ok(slideService.getAllSlides());
    }

}
