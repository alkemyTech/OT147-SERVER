package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    //As an Admin see all details in the news entity
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getDetailsById(@PathVariable String id) {
        NewsDto newsDto = newsService.getDetailsById(id);
        return ResponseEntity.ok(newsDto);
    }
    //Create news entity only by Admin.
    @PostMapping()
    public ResponseEntity<NewsDto> create(@RequestBody @Valid NewsDto dto) {
        try {
            return ResponseEntity.ok(newsService.save(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
