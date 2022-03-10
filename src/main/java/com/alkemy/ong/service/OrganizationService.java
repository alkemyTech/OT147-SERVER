package com.alkemy.ong.service;



import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.dto.OrganizationUpdateDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrganizationService {

    private final OrganizationMapper organizationMapper;
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
                    "There is no Organization with the entered Id");
        }
    }
  // Service to Update Organization if Exist
  public OrganizationPublicDto update(OrganizationUpdateDto dto) {
      if(!organizationRepository.existsById(dto.getId())){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                  "There is no Organization with the entered Id");
      }
      OrganizationEntity organization=organizationRepository.getById(dto.getId());
      organization.setName(dto.getName());
      organization.setImage(dto.getImage());
      organization.setPhone(dto.getPhone());
      organization.setAddress(dto.getAddress());
      organization.setFacebookUrl(dto.getFacebookUrl());
      organization.setLinkedinUrl(dto.getLinkedinUrl());
      organization.setInstagramUrl(dto.getInstagramUrl());
      organizationRepository.save(organization);
      return organizationMapper.organizationToOrganizationPublicDto(organization);
  }
}
