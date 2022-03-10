package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    //Members creation method for User
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody MemberDto memberDto)  {
            MemberDto savedMember = memberService.addMember(memberDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    //Method to get a list of members
    @GetMapping()
    public ResponseEntity<List<MemberDto>> membersList() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }
}
