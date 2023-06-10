package com.crud.contacts.mapper;

import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.model.dto.ContactDTO;
import com.crud.contacts.model.dto.EmailDTO;
import com.crud.contacts.model.dto.PhoneNumberDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {
    public Contact mapToContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());

        List<PhoneNumber> phoneNumbers = mapToPhoneNumbers(contactDTO.getPhoneNumbers());
        contact.setPhoneNumbers(phoneNumbers);

        List<Email> emails = mapToEmails(contactDTO.getEmails());
        contact.setEmails(emails);
        return contact;
    }

    public ContactDTO mapToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());

        List<PhoneNumberDTO> phoneNumberDTOs = contact.getPhoneNumbers().stream().map(this::mapToPhoneNumberDTO).toList();
        contactDTO.setPhoneNumbers(phoneNumberDTOs);

        List<EmailDTO> emailDTOs = contact.getEmails().stream().map(this::mapToEmailDTO).toList();
        contactDTO.setEmails(emailDTOs);
        return contactDTO;
    }

    public List<ContactDTO> mapToContactDTOList(List<Contact> contacts) {
        return contacts.stream()
                .map(this::mapToContactDTO)
                .collect(Collectors.toList());
    }

    public PhoneNumber mapToPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
        phoneNumber.setLabel(phoneNumberDTO.getLabel());
        return phoneNumber;
    }

    public PhoneNumberDTO mapToPhoneNumberDTO(PhoneNumber phoneNumber) {
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
        phoneNumberDTO.setLabel(phoneNumber.getLabel());
        return phoneNumberDTO;
    }

    public List<PhoneNumber> mapToPhoneNumbers(List<PhoneNumberDTO> phoneNumberDTOs) {
        return phoneNumberDTOs.stream()
                .map(this::mapToPhoneNumber)
                .collect(Collectors.toList());
    }

    public Email mapToEmail(EmailDTO emailDTO) {
        Email email = new Email();
        email.setEmail(emailDTO.getEmail());
        email.setDomainName(emailDTO.getDomainName());
        return email;
    }

    public EmailDTO mapToEmailDTO(Email email) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(email.getEmail());
        emailDTO.setDomainName(email.getDomainName());
        return emailDTO;
    }

    public List<Email> mapToEmails(List<EmailDTO> emailDTOs) {
        return emailDTOs.stream()
                .map(this::mapToEmail)
                .collect(Collectors.toList());
    }
}
