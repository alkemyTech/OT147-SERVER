package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.SlideEntity;
import com.alkemy.ong.service.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    SlideService slideService;
    //Retrieve slides list only for Admin
    @GetMapping
    public ResponseEntity<List<SlideBasicDto>> getAllSlides(){
        return ResponseEntity.ok(slideService.getAllSlides());
    }

    //Slides Soft deletion method by id for Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<SlideEntity> delete(@PathVariable String id) {
        try {
            this.slideService.deleteSlideById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlideDtoFull> update(@PathVariable String id, @RequestBody SlideDtoFull slideDtoFull) {
        return ResponseEntity.ok(slideService.update(id, slideDtoFull));
    }

    //Get slide by id only for Admin
    @GetMapping("/{id}")
    public ResponseEntity<SlideDtoFull> getSlide(
            @PathVariable(name="id", required = true)
            String id) {
        return ResponseEntity.ok(slideService.getSlide(id));
    }
}
