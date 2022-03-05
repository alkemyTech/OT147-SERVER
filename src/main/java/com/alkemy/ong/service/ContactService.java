package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.entity.ContactEntity;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    ContactMapper contactMapper;

    @Autowired
    ContactRepository contactRepository;

    public ContactDto saveContact(ContactDto dto){
        ContactEntity entities = contactMapper.contactDtoToContactEntity(dto);
        ContactEntity saveEntity = contactRepository.save(entities);
        ContactDto contactDto = contactMapper.contactEntityToContactDto(saveEntity);
        return  contactDto;

    }
}
