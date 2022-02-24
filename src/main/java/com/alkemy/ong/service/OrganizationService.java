package com.alkemy.ong.service;



import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private  OrganizationMapper organizationMapper;
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
  // Service to Update Organization if Exits
    public OrganizationDto update(OrganizationDto dto) {
        if(!organizationRepository.existsById(dto.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Organization with the entered Id");
        }
        var organization=organizationRepository
                .save(organizationMapper.organizationDtoToOrganization(dto));
        return organizationMapper.organizationToOrganizationDto(organization);

    }
}
