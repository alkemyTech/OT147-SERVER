package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private  MemberService memberService;
    //Members creation method for User
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody MemberDto memberDto)  {
            MemberDto savedMember = memberService.addMember(memberDto);
            return status(HttpStatus.CREATED).body(savedMember);
    }
    //Delete member by id (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception {
        try {
            memberService.deleteMemberById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Method to get a list of members
    // Method to get a list of members with 10 members role User
    @GetMapping()
    public ResponseEntity<?> membersPageable(@RequestParam (required= false, defaultValue = "-1") int page) {
        if(page!=-1) {
            PagesDto<MemberDto> response = memberService.searchPaginatedMembers(page);
            return ResponseEntity.ok().body(response);
        }
        List<MemberDto> response = memberService.getAllMembers();
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable String id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.update(id, memberDto));
    }
}



