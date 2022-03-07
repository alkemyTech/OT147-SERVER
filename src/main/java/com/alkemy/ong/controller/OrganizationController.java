package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SlideService slideService;


    //Get public information of an Organization
    @GetMapping("/public/{organizationId}")
    public ResponseEntity<List<SlideDto>> getOrganization(
            @PathVariable(name="organizationId", required=true)
                    String organizationId){

        try {
            return ResponseEntity.ok(slideService.getSlidesForOrganizationByOrder(organizationId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Category with the entered Id");
        }

    }

    //Update public information of an Organization only for Admin User
    @PutMapping("/public")
    public ResponseEntity<OrganizationDto> update(@RequestBody @Valid OrganizationDto organizationDto){

        return ResponseEntity.ok(organizationService.update(organizationDto));
    }

    @PostMapping("/public")
    public ResponseEntity<OrganizationDto> create(@RequestBody @Valid OrganizationDto organizationDto){

        return ResponseEntity.ok(organizationService.create(organizationDto));
    }
}
