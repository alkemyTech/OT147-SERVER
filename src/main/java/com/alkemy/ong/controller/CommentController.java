package com.alkemy.ong.controller;


import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private  CommentService commentService;
    //Create news entity only by User.
    @PostMapping()
    public ResponseEntity<CommentDto> create(@RequestBody @Valid CommentDto dto) {
        try {
            return ResponseEntity.ok(commentService.save(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
