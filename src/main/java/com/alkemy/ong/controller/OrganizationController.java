package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;

import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    //Get public information of an Organization
    @GetMapping("/public/{organizationId}")
    public ResponseEntity<OrganizationDto> getOrganization(
            @PathVariable(name="organizationId", required=true)
                    String organizationId){
        return ResponseEntity.ok(organizationService.getPublicOrganization(organizationId));
    }
    //Update public information of an Organization only for Admin User
    @PutMapping("/public")
    public ResponseEntity<OrganizationDto> update(@RequestBody @Valid OrganizationDto organizationDto){

        return ResponseEntity.ok(organizationService.update(organizationDto));
    }
}
