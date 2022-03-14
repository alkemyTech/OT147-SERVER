package com.alkemy.ong.controller;


import com.alkemy.ong.dto.CommentBodyDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private  CommentService commentService;
    //Create news Comment only by User.
    @PostMapping()
    public ResponseEntity<CommentDto> create(@RequestBody @Valid CommentDto dto) {
        try {
            return ResponseEntity.ok(commentService.save(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    //Get all Comment Body Only for ADMIN role.
    @GetMapping()
    public ResponseEntity<List<CommentBodyDto>> getAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    //Delete Comment for id only for User creator or Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Authentication aut, @PathVariable String id){
        if(this.commentService.existId(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            this.commentService.delete(aut,id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
