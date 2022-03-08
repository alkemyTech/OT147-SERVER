package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.entity.ContactEntity;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ContactService {

    ContactMapper contactMapper;

    @Autowired
    ContactRepository contactRepository;

    // Method to save Contact in the DB for User
    public ContactDto saveContact(ContactDto contactDto) throws Exception {
       ContactDto validDto = this.validate(contactDto);
       ContactEntity saveContact = ContactMapper.contactMapper.contactDtoToContactEntity(validDto);
       ContactEntity contact = contactRepository.save(saveContact);
       return ContactMapper.contactMapper.contactEntityToContactDto(contact);

    }

    // Method to Validate the Contact name and email fields are not empty
    public ContactDto validate(ContactDto contactDto) throws ResponseStatusException {
        if (contactDto.getName().trim().isEmpty() && contactDto.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The name and email fields can not be empty");
        } else if (contactDto.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The name can not be empty");
        } else if (contactDto.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"The email can not be empty");
        }
        return contactDto;
    }

    // Method to get all the contacts for the Admin
    public List<ContactDto> getAllContacts() {

        List<ContactEntity> contactEntityList = contactRepository.findAll();
        return ContactMapper.contactMapper.listContactEntityToListContactDto(contactEntityList);

    }
}
