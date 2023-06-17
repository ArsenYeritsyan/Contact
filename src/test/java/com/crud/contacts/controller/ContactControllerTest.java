package com.crud.contacts.controller;

import com.crud.contacts.mapper.ContactMapper;
import com.crud.contacts.config.ContactSpecification;
import com.crud.contacts.exception.RestExceptionHandler;
import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.model.dto.ContactDTO;
import com.crud.contacts.model.dto.EmailDTO;
import com.crud.contacts.model.dto.PhoneNumberDTO;
import com.crud.contacts.service.ContactService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactMapper contactMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void updateContactTest() {
        Long contactId = 1L;
        Contact contact = new Contact();

        when(contactService.getContactById(contactId)).thenReturn(Optional.of(contact));
        when(contactService.updateContact(any(Contact.class))).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.updateContact(contactId, contact);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void deleteContactTest() {
        Long contactId = 1L;

        ResponseEntity<Void> response = contactController.deleteContact(contactId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void getContactByIdTest() {
        Long contactId = 1L;
        Contact contact = new Contact();

        when(contactService.getContactById(contactId)).thenReturn(Optional.of(contact));
        when(contactMapper.toContactDTO(contact)).thenReturn(new ContactDTO());

        ResponseEntity<ContactDTO> response = contactController.getContactById(contactId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }


    @Test
    public void getContactById_NonExistingContact_ReturnsNotFound() throws Exception {

        Long contactId = 1L;
        when(contactService.getContactById(contactId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/contacts/{id}", contactId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testUpdatePhoneNumber() {
        Long contactId = 1L;
        Long phoneNumberId = 2L;
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        PhoneNumber updatedPhoneNumber = new PhoneNumber();
        PhoneNumberDTO updatedPhoneNumberDTO = new PhoneNumberDTO();
        when(contactService.updatePhoneNumber(contactId, phoneNumberId, contactMapper.toPhoneNumber(phoneNumberDTO)))
                .thenReturn(updatedPhoneNumber);
        when(contactMapper.toPhoneNumberDTO(updatedPhoneNumber)).thenReturn(updatedPhoneNumberDTO);

        ResponseEntity<PhoneNumberDTO> response = contactController.updatePhoneNumber(contactId, phoneNumberId, phoneNumberDTO);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(updatedPhoneNumberDTO, response.getBody());
    }


    @Test
    public void searchContacts_NoParametersProvided() {
        List<Contact> expectedContacts = new ArrayList<>();

        when(contactService.searchContacts(any())).thenReturn(expectedContacts);

        ResponseEntity<List<Contact>> response = contactController.searchContacts(null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContacts, response.getBody());
    }

    @Test
    public void searchContacts_PhoneNumberProvided() {
        String phoneNumber = "123456789";

//        Specification<Contact> expectedSpec = ContactSpecification.phoneNumberContains(phoneNumber);

        List<Contact> expectedContacts = new ArrayList<>();


        ResponseEntity<List<Contact>> response = contactController.searchContacts(phoneNumber, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContacts, response.getBody());
    }


    @Test
    public void searchContacts_NoMatchingContacts_ReturnsEmptyList() {

        String phoneNumber = "123456789";

//        Specification<Contact> expectedSpec = ContactSpecification.phoneNumberContains(phoneNumber);

        List<Contact> expectedContacts = new ArrayList<>();


        ResponseEntity<List<Contact>> response = contactController.searchContacts(phoneNumber, null, null, null);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContacts, response.getBody());
    }

    @Test
    public void searchContacts_PhoneNumberParameterOnly_ReturnsMatchingContacts() {
        String phoneNumber = "123456789";

//        Specification<Contact> expectedSpec = ContactSpecification.phoneNumberContains(phoneNumber);

        List<Contact> expectedContacts = new ArrayList<>();

        ResponseEntity<List<Contact>> response = contactController.searchContacts(phoneNumber, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContacts, response.getBody());
    }

    @Test
    public void testDeletePhoneNumber() {
        Long contactId = 1L;
        Long phoneNumberId = 2L;

        ResponseEntity<Void> response = contactController.deletePhoneNumber(contactId, phoneNumberId);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contactService).deletePhoneNumber(contactId, phoneNumberId);
    }

    @Test
    public void testAddEmail() {
        Long contactId = 1L;
        EmailDTO emailDTO = new EmailDTO();
        Email createdEmail = new Email();
        EmailDTO createdEmailDTO = new EmailDTO();
        when(contactService.addEmail(contactId, contactMapper.toEmail(emailDTO)))
                .thenReturn(createdEmail);
        when(contactMapper.toEmailDTO(createdEmail)).thenReturn(createdEmailDTO);

        ResponseEntity<EmailDTO> response = contactController.addEmail(contactId, emailDTO);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(createdEmailDTO, response.getBody());
    }

    @Test
    public void testUpdateEmail() {
        Long contactId = 1L;
        Long emailId = 2L;
        EmailDTO emailDTO = new EmailDTO();
        Email updatedEmail = new Email();
        EmailDTO updatedEmailDTO = new EmailDTO();
        when(contactService.updateEmail(contactId, emailId, contactMapper.toEmail(emailDTO)))
                .thenReturn(updatedEmail);
        when(contactMapper.toEmailDTO(updatedEmail)).thenReturn(updatedEmailDTO);

        ResponseEntity<EmailDTO> response = contactController.updateEmail(contactId, emailId, emailDTO);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(updatedEmailDTO, response.getBody());
    }

    @Test
    public void searchContactsByPhoneNumberTest() {
        String phoneNumber = "1234567890";

        Specification<Contact> spec = ContactSpecification.phoneNumberContains(phoneNumber);

        List<Contact> contacts = new ArrayList<>();

        List<Contact> result = contactService.searchContacts(spec);

        assertEquals(contacts, result);
    }
}
