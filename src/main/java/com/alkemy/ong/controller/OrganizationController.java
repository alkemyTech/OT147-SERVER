package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
