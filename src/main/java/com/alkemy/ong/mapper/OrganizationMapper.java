package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationMapper organizationMapper = Mappers.getMapper(OrganizationMapper.class);

    OrganizationDto organizationToOrganizationDto(OrganizationEntity organization);

    OrganizationEntity organizationDtoToOrganization(OrganizationDto dto);

    OrganizationEntity organizationPublicDtoToOrganization(OrganizationPublicDto organizationPublicDto);

    OrganizationPublicDto organizationToOrganizationPublicDto(OrganizationEntity organization);

}
