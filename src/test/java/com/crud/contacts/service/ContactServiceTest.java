package com.crud.contacts.service;

import com.crud.contacts.config.ContactSpecification;
import com.crud.contacts.model.Contact;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.repo.ContactRepository;
import com.crud.contacts.repo.PhoneNumberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @InjectMocks
    private ContactService contactService;


    @Test
    public void createContactTest() {
        Contact contact = new Contact();

        when(contactRepository.save(contact)).thenReturn(contact);

        Contact result = contactService.createContact(contact);

        assertEquals(contact, result);
    }

    @Test
    public void getContactByIdTest() {
        Long contactId = 1L;
        Contact contact = new Contact();

        when(contactRepository.findByIdAndDeletedFalse(contactId)).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getContactById(contactId);

        assertTrue(result.isPresent());
        assertEquals(contact, result.get());
    }


    @Test
    public void searchContactsByPhoneNumberTest() {
        String phoneNumber = "1234567890";

        Specification<Contact> spec = ContactSpecification.phoneNumberContains(phoneNumber);
        List<Contact> contacts = new ArrayList<>();

        when(contactRepository.findAll(spec)).thenReturn(contacts);

        List<Contact> result = contactService.searchContacts(spec);

        assertEquals(contacts, result);
    }

    @Test
    public void searchContactsByEmailTest() {
        String email = "example@example.com";

        Specification<Contact> spec = ContactSpecification.emailContains(email);
        List<Contact> contacts = new ArrayList<>();

        when(contactRepository.findAll(spec)).thenReturn(contacts);

        List<Contact> result = contactService.searchContacts(spec);

        assertEquals(contacts, result);
    }

    @Test
    public void searchContactsByFirstNameTest() {
        String firstName = "John";

        Specification<Contact> spec = ContactSpecification.firstNameContains(firstName);
        List<Contact> contacts = new ArrayList<>();

        when(contactRepository.findAll(spec)).thenReturn(contacts);

        List<Contact> result = contactService.searchContacts(spec);

        assertEquals(contacts, result);
    }

    @Test
    public void deleteContactTest() {
        Long contactId = 1L;
        Contact contact = new Contact();

        when(contactRepository.findByIdAndDeletedFalse(contactId)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        contactService.deleteContact(contactId);

        assertTrue(contact.isDeleted());
    }

    @Test
    public void updatePhoneNumberTest() {
        Long contactId = 1L;
        Long phoneNumberId = 1L;
        Contact contact = new Contact();

        PhoneNumber existingPhoneNumber = new PhoneNumber();
        PhoneNumber updatedPhoneNumber = new PhoneNumber();

        when(contactRepository.findByIdAndDeletedFalse(contactId)).thenReturn(Optional.of(contact));
        when(phoneNumberRepository.findById(phoneNumberId)).thenReturn(Optional.of(existingPhoneNumber));
        when(phoneNumberRepository.save(any(PhoneNumber.class))).thenReturn(updatedPhoneNumber);

        PhoneNumber result = contactService.updatePhoneNumber(contactId, phoneNumberId, updatedPhoneNumber);

        assertEquals(updatedPhoneNumber.getPhoneNumber(), result.getPhoneNumber());
    }


}
