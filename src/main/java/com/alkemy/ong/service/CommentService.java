package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.CommentEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}