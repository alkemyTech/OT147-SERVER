package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentBodyDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.CommentEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private static CommentMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = Mappers.getMapper(CommentMapper.class);

    }

    @Test
    void CommentDtoToCommentEntity() {
        CommentDto dto = new CommentDto();
        dto.setId("1243442");
        CommentEntity ent = mapper.commentDtoToCommentEntity(dto);
        System.out.println(ent.getId());
        assertAll(
                () -> {
                    assertEquals(dto.getId(), ent.getId());
                }
        );
    }

    @Test
    public void commentDtoMapperTestNull() {
        CommentDto dto = null;
        CommentEntity entity = mapper.commentDtoToCommentEntity(dto);
        assertEquals(null, entity);
    }

    @Test
    void commentEntityToCommentDto() {
        CommentEntity comment = new CommentEntity();
        comment.setId("1234");
        comment.setSoftDelete(false);
        comment.setTimestamps(LocalDateTime.now());

        CommentDto dto = mapper.commentEntityToCommentDto(comment);
        System.out.println(dto.getId());
        assertAll(
                () -> {
                    assertEquals(dto.getId(), comment.getId());
                    assertEquals(dto.getSoftDelete(), comment.getSoftDelete());
                }
        );
    }
    @Test
    void commentEntityToCommentDtoNull() {
        CommentEntity comment = null;
        CommentDto dto = mapper.commentEntityToCommentDto(comment);
        assertEquals(null, dto);

    }

    @Test
    void listCommentEntityToListCommentDtoFull() {
        CommentEntity comment = new CommentEntity();
        comment.setId("1234");
        comment.setSoftDelete(false);
        comment.setTimestamps(LocalDateTime.now());

        List<CommentEntity> commentList = new ArrayList<>(Arrays.asList(comment));
        List<CommentDto> commentDtoList = mapper.listCommentEntityToListCommentDto(commentList);

        assertAll(
                () -> {
                    assertEquals(commentDtoList.get(0).getId(), comment.getId());
                    assertEquals(commentDtoList.get(0).getSoftDelete(), comment.getSoftDelete());

                }
        );

    }
    @Test
    void listCommentEntityToListCommentDtoFullNull() {
        CommentEntity comment = null;
        List<CommentEntity> commentList = new ArrayList<>(Arrays.asList(comment));
        List<CommentDto> commentDtoList = mapper.listCommentEntityToListCommentDto(commentList);

        assertEquals(null,comment);

    }
    @Test
    void listCommentEntityToListCommentDtoFullNullList() {
        CommentEntity comment = null;
        List<CommentEntity> commentList = null;
        List<CommentDto> commentDtoList = mapper.listCommentEntityToListCommentDto(commentList);

        assertEquals(null, commentList);

    }
    @Test
    void commentEntityToCommentBodyDto() {
        CommentEntity comment = new CommentEntity();
        comment.setId("1234");
        comment.setBody("Body");
        CommentBodyDto dto = mapper.commentEntityToCommentBodyDto(comment);
        assertAll(
                () -> {
                    assertEquals(dto.getBody(), comment.getBody());
                }
        );
    }
    @Test
    void commentEntityToCommentBodyDtoNull() {
        CommentEntity comment = null;
        CommentBodyDto dto = mapper.commentEntityToCommentBodyDto(comment);
        assertEquals(null, comment);
    }
    @Test
    void listCommentEntityToListCommentBodyDtoFull() {
        CommentEntity comment = new CommentEntity();
        comment.setBody("Body");

        List<CommentEntity> commentList = new ArrayList<>(Arrays.asList(comment));
        List<CommentBodyDto> commentBodyDtoList = mapper.commentEntityListToCommentBodyDtoList(commentList);

        assertAll(
                () -> {
                    assertEquals(commentBodyDtoList.get(0).getBody(), comment.getBody());
                }
        );
    }
    @Test
    void listCommentEntityToListCommentBodyDtoFullNullList() {
        CommentEntity comment = null;
        List<CommentEntity> commentList = null;
        List<CommentBodyDto> commentDtoList = mapper.commentEntityListToCommentBodyDtoList(commentList);
        assertEquals(null, commentList);

    }
}