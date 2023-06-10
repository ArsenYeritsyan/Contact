package com.crud.contacts.config;

import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.model.dto.ContactDTO;
import com.crud.contacts.model.dto.EmailDTO;
import com.crud.contacts.model.dto.PhoneNumberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
//@Component
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDTO toContactDTO(Contact contact);

    Contact toContact(ContactDTO contactDTO);

    PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber);

    PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO);

    EmailDTO toEmailDTO(Email email);

    Email toEmail(EmailDTO emailDTO);

    default ContactMapper getInstance() {
        return INSTANCE;
    }
}
