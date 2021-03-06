package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBodyDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.CommentEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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

    public List<CommentDto> getCommentsByNewsId(String newsId) {
        List<CommentEntity> commentsList = commentRepository.findCommentsByNewsId(newsId);
        if (commentsList.isEmpty()) {
            throw new ParamNotFound("There are no comments with the provided News id");
        } else {
            return CommentMapper.commentMapper.listCommentEntityToListCommentDto(commentsList);
        }
    }

    public List<CommentBodyDto> getAll() {
        return commentMapper.commentEntityListToCommentBodyDtoList(commentRepository.findAllByOrder());
    }

    @Transactional
    public ResponseEntity<?> delete(Authentication aut, String id) {
        if (verifyId(aut, id)) {
            CommentEntity entity = commentRepository.getById(id);
            entity.setSoftDelete(true);
            commentRepository.save(entity);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new ParamNotFound("There is no comment with the entered Id");
        }
    }

    public boolean verifyId(Authentication auth, String id) {
        String username = auth.getName();
        var commentEntityOptional = commentRepository.findById(id);
        if (commentEntityOptional.isPresent()) {
            CommentEntity comment = commentEntityOptional.get();
            String emailUserCreator = comment.getUserId().getEmail();

            String authorityUser = auth.getAuthorities().stream().findFirst().get().toString();

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

    public CommentDto update(Authentication auth, CommentDto commentDto, String id) {
        if (verifyId(auth, id)) {
            CommentEntity commentEntity = commentRepository.getById(id);
            commentEntity.setBody(commentDto.getBody());
            commentEntity.setNewsId(commentDto.getNewsId());
            return commentMapper.commentEntityToCommentDto(commentRepository.save(commentEntity));
        }
        throw new ParamNotFound("There is no comment with the entered Id");
    }
}