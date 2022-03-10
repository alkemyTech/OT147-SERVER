package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;

import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.dto.SlidePublicOrganizationDto;
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
    public ResponseEntity<List<SlidePublicOrganizationDto>> getOrganization(
            @PathVariable(name="organizationId", required=true)
                    String organizationId){

        return ResponseEntity.ok(slideService.getSlidesForOrganizationByOrder(organizationId));

    }
    //Update public information of an Organization only for Admin User
    @PutMapping("/public")
    public ResponseEntity<OrganizationPublicDto> update(@RequestBody @Valid OrganizationPublicDto organizationPublicDto){

        return ResponseEntity.ok(organizationService.update(organizationPublicDto));
    }
}
