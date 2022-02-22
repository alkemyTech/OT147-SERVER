package com.alkemy.ong.service;


import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    //Service to retrieve an Organization from database. Check if its present.
    public OrganizationDto getPublicOrganization(String organizationId) {
        Optional<OrganizationEntity> organization = organizationRepository.findById(organizationId);
        if (organization.isPresent()) {
            OrganizationDto organizationDto = OrganizationMapper.organizationMapper.organizationToOrganizationDto(organization.get());
            return organizationDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se encontró organización con esos datos");
        }
    }
}
