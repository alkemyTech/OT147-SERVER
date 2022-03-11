package com.alkemy.ong.controller;

import com.alkemy.ong.dto.*;

import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<List<SlidePublicOrganizationDto>> getOrganization(
            @PathVariable(name="organizationId", required=true)
                    String organizationId){

        return ResponseEntity.ok(slideService.getSlidesForOrganizationByOrder(organizationId));

    }
    //Update public information of an Organization only for Admin User
    @PutMapping("/public")
    public ResponseEntity<OrganizationPublicDto> update(@RequestBody @Valid OrganizationUpdateDto organizationupdateDto){

        return ResponseEntity.ok(organizationService.update(organizationupdateDto));
    }
}
