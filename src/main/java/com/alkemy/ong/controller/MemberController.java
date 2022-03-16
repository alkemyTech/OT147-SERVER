package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.util.DocumentationMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Tag(name = "Members", description = "Create, update show and delete Members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //Members creation method for User
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_CREATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_201_DESCRIPTION)})
    @Tag(name = "Members")
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody MemberDto memberDto) {
        MemberDto savedMember = memberService.addMember(memberDto);
        return status(HttpStatus.CREATED).body(savedMember);
    }

    //Delete member by id (soft delete)
    @DeleteMapping(value = "/{id}")
    @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = "403",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = "404",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    @Tag(name = "Members")
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception {
        try {
            memberService.deleteMemberById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Method to get a list of members with 10 members role User
    @Tag(name = "Members")
    @GetMapping(params = "page")
    @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_LIST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "400",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = "403",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    public ResponseEntity<?> membersPageable(@RequestParam(required = false, defaultValue = "-1") int page) {
        if (page != -1) {
            PagesDto<MemberDto> response = memberService.searchPaginatedMembers(page);
            return ResponseEntity.ok().body(response);
        }
        List<MemberDto> response = memberService.getAllMembers();
        return ResponseEntity.ok().body(response);
    }

    // Method to update  of members
    @Tag(name = "Members")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "403",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = "404",
                    description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    public ResponseEntity<MemberDto> update(@PathVariable String id, @Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.update(id, memberDto));
    }
}