package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.util.DocumentationMessages;
import com.alkemy.ong.util.DocumentationResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news")
@Tag(name = "News", description = "Create, update, show and delete News")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    //get Pageable list of News
    @Tag(name = "News")
    @Operation(summary = DocumentationMessages.NEWS_CONTROLLER_SUMMARY_LIST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_BAD_REQUEST,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    @GetMapping
    public ResponseEntity<?> getPageNews(@RequestParam(defaultValue = "0") int page){
        PagesDto<NewsDto> response = newsService.getAllPagesNews(page);
        return ResponseEntity.ok().body(response);
    }

    //As an Admin see all details in the news entity
    @Tag(name = "News")
    @Operation(summary=DocumentationMessages.NEWS_CONTROLLER_SUMMARY_READ )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_BAD_REQUEST,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getDetailsById(@PathVariable String id) {
        NewsDto newsDto = newsService.getDetailsById(id);
        return ResponseEntity.ok(newsDto);
    }
    //List comments by news id role User
    @Tag(name = "News")
    @Operation(summary= DocumentationMessages.NEWS_CONTROLLER_SUMMARY_COMMENTS )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_BAD_REQUEST,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    @GetMapping(value="/{id}/comments" ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDto>> listCommentsByNewsId(@PathVariable(value = "id") String id){
        List<CommentDto> commentsDtoList = commentService.getCommentsByNewsId(id);
        return ResponseEntity.ok(commentsDtoList);
    }
    //Create news entity only by Admin
    @Tag(name = "News")
    @Operation(summary=DocumentationMessages.NEWS_CONTROLLER_SUMMARY_CREATE )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDto> create(@RequestBody @Valid NewsDto dto) {
        try {
            return ResponseEntity.ok(newsService.save(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    //Update information of News only by Admin
    @Tag(name = "News")
    @Operation(summary=DocumentationMessages.NEWS_CONTROLLER_SUMMARY_UPDATE )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    @PutMapping( value ="/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDto> update(@PathVariable String id, @RequestBody NewsDto dto) {
        try {
            return ResponseEntity.ok(newsService.update(id, dto));
        } catch (Exception e) {
            throw new ParamNotFound("There are no news with the provided News id");
        }
    }
    //Delete News for id  only by Admin
    @Tag(name = "News")
    @Operation(summary=DocumentationMessages.NEWS_CONTROLLER_SUMMARY_DELETE  )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.NEWS_CONTROLLER_RESPONSE_404_DESCRIPTION)})
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
