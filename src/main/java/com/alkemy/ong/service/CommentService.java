package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBodyDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.CommentEntity;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

        @Autowired
        private CommentRepository commentRepository;
        @Autowired
        private CommentMapper commentMapper;

        @Transactional
        public CommentDto save(CommentDto dto) {
            CommentEntity comment = commentMapper.commentDtoToCommentEntity(dto);
            return commentMapper.commentEntityToCommentDto(commentRepository.save(comment));
        }

         public List<CommentBodyDto> getAll() {
            return commentMapper.commentEntityListToCommentBodyDtoList(commentRepository.findAllByOrder());
        }

    @Transactional
    public ResponseEntity<?> delete(Authentication aut, String id) {
        if ( verifyId(aut, id)) {
            CommentEntity entity = commentRepository.getById(id);
            entity.setSoftDelete(true);
            commentRepository.save(entity);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no News with the entered Id");
        }
    }

    public boolean verifyId(Authentication aut, String id) {
        String username = aut.getName();
        var commentEntityOptional = commentRepository.findById(id);
        if (commentEntityOptional.isPresent()) {
            CommentEntity comment = commentEntityOptional.get();
            String emailUserCreator = comment.getUserId().getEmail();

            String authorityUser = aut.getAuthorities().stream().findFirst().get().toString();

            if (username.equals(emailUserCreator)
                    || authorityUser.equals("ADMIN")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public boolean existId(String id) {
       return commentRepository.findById(id).isEmpty();
    }
    }
