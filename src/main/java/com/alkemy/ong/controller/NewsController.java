package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;
    //get Pageable list of News
    @GetMapping
    public ResponseEntity<?> getPageNews(@RequestParam(defaultValue = "0") int page){
        PagesDto<NewsDto> response = newsService.getAllPagesNews(page);
        return ResponseEntity.ok().body(response);
    }

    //As an Admin see all details in the news entity
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getDetailsById(@PathVariable String id) {
        NewsDto newsDto = newsService.getDetailsById(id);
        return ResponseEntity.ok(newsDto);
    }
    //List comments by news id role User
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> listCommentsByNewsId(@PathVariable(value = "id") String id){
        List<CommentDto> commentsDtoList = commentService.getCommentsByNewsId(id);
        return ResponseEntity.ok(commentsDtoList);
    }
    //Create news entity only by Admin
    @PostMapping()
    public ResponseEntity<NewsDto> create(@RequestBody @Valid NewsDto dto) {
        try {
            return ResponseEntity.ok(newsService.save(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    //Update information of News only by Admin
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> update(@PathVariable String id, @RequestBody NewsDto dto) {
        try {
            return ResponseEntity.ok(newsService.update(id, dto));
        } catch (Exception e) {
            throw new ParamNotFound("There are no news with the provided News id");
        }
    }
    //Delete News for id  only by Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<NewsDto> delete(@PathVariable String id){
        try{
            this.newsService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            throw new ParamNotFound("There are no news with the provided News id");
        }
    }
}
