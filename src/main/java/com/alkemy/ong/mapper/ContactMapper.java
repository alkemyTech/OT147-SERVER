package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper contactMapper= Mappers.getMapper(ContactMapper.class);

    ContactEntity contactDtoToContactEntity(ContactDto contactDto);

    ContactDto contactEntityToContactDto(ContactEntity contactEntity);

    List<ContactDto> listContactEntityToListContactDto(List<ContactEntity> list);


}


