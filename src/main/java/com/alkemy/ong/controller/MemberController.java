package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.service.MemberService;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //Method to get a list of members
    @GetMapping()
    public ResponseEntity<List<MemberDto>> membersList() {
        List<MemberDto> members = memberService.getAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }
    //delete member by id (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception {
        try {
            memberService.deleteMemberById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Method to get a list of members with 10 members
    @GetMapping("/{page}")
    public ResponseEntity<?> membersPageable(@PathVariable("page") int page)throws ParameterNotFoundException {
        try {
            Map<String, Object> response = new HashMap<>();
            if (page > 0) {
                response.put("url previous", String.format("localhost:8080/members/%d", page - 1));
            }
            if (!this.memberService.getPaginated(page + 1).isEmpty()) {
                response.put("url next", String.format("localhost:8080/members/%d", page + 1));
            }
            response.put("ok", memberService.getPaginated(page));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ParamNotFound("The was an error retrieving the list of members");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable String id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.update(id, memberDto));
    }
}



