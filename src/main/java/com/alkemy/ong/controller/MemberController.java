package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.service.MemberService;
import javassist.compiler.ast.Variable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

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

   // Method to get a list of members by 10 pages
    @GetMapping("/{page}")
    public Page<MemberEntity> membersPageable(@PathVariable int page){
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<MemberEntity> members = memberService.findAllMembers(pageable);
        if (members.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The members list is empty");
        }
        return members;
    }
}
