package com.crud.contacts.mapper;

import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.model.dto.ContactDTO;
import com.crud.contacts.model.dto.EmailDTO;
import com.crud.contacts.model.dto.PhoneNumberDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {

    ContactDTO toContactDTO(Contact contact);

    Contact toContact(ContactDTO contactDTO);

    PhoneNumberDTO toPhoneNumberDTO(PhoneNumber phoneNumber);

    PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO);

    EmailDTO toEmailDTO(Email email);

    Email toEmail(EmailDTO emailDTO);
}
