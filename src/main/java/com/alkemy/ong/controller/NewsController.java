package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    //As an Admin see all details in the news entity
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getDetailsById(@PathVariable String id) {
        NewsDTO newsDTO = newsService.getDetailsById(id);
        return ResponseEntity.ok(newsDTO);
    }
}
