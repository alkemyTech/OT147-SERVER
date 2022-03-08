package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    //Endpoint to get all contacts for an Admin


    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(){

            return ResponseEntity.ok(contactService.getAllContacts());
        
    }

    //Update information of Contact for User
    @PostMapping
    public ResponseEntity<ContactDto> save(@RequestBody ContactDto contactDto) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.saveContact(contactDto));
    }
}