package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper {
    OrganizationMapper organizationMapper = Mappers.getMapper(OrganizationMapper.class);

    OrganizationDto organizationToOrganizationDto(OrganizationEntity organization);

<<<<<<< HEAD
=======
    OrganizationEntity organizationDtoToOrganization(OrganizationDto dto);
>>>>>>> 14cd07718014ec2e11e53a07318bcd3f83810ac4
}
