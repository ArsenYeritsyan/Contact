package com.crud.contacts.controller;

import com.crud.contacts.mapper.ContactMapper;
import com.crud.contacts.config.ContactSpecification;
import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.model.dto.ContactDTO;
import com.crud.contacts.model.dto.EmailDTO;
import com.crud.contacts.model.dto.PhoneNumberDTO;
import com.crud.contacts.service.ContactService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ContactMapper contactMapper;

    public ContactController(ContactService contactService, ContactMapper mapper) {
        this.contactService = contactService;
        this.contactMapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactMapper.toContact(contactDTO);
        Contact createdContact = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactMapper.toContactDTO(createdContact));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<Contact> contactOptional = contactService.getContactById(id);
        return contactOptional.map(contact -> ResponseEntity.ok(contactMapper.toContactDTO(contact))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<Contact>> getContactsList(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Contact> contacts = contactService.getContactsList(pageable);
        return ResponseEntity.ok().body(contacts);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> searchContacts(@RequestParam("search") String searchTerm) {
        Specification<Contact> searchSpec = ContactSpecification.searchContacts(searchTerm);
        List<Contact> contacts = contactService.searchContacts(searchSpec);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> searchContacts(
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        Specification<Contact> spec = Specification.where(null);

        if (phoneNumber != null) {
            spec = spec.and(ContactSpecification.phoneNumberContains(phoneNumber));
        }

        if (email != null) {
            spec = spec.and(ContactSpecification.emailContains(email));
        }

        if (firstName != null) {
            spec = spec.and(ContactSpecification.firstNameContains(firstName));
        }

        if (lastName != null) {
            spec = spec.and(ContactSpecification.lastNameContains(lastName));
        }

        List<Contact> contacts = contactService.searchContacts(spec);
        return ResponseEntity.ok().body(contacts);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        Optional<Contact> contactOptional = contactService.getContactById(id);
        if (contactOptional.isPresent()) {
            contact.setId(id);
            Contact updatedContact = contactService.updateContact(contact);
            return ResponseEntity.ok().body(updatedContact);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{contactId}/phoneNumbers")
    public ResponseEntity<PhoneNumberDTO> addPhoneNumber(@PathVariable Long contactId, @RequestBody PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = contactMapper.toPhoneNumber(phoneNumberDTO);
        PhoneNumber createdPhoneNumber = contactService.addPhoneNumber(contactId, phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactMapper.toPhoneNumberDTO(createdPhoneNumber));
    }


    @PutMapping("/{contactId}/phoneNumbers/{phoneNumberId}")
    public ResponseEntity<PhoneNumberDTO> updatePhoneNumber(
            @PathVariable Long contactId,
            @PathVariable Long phoneNumberId,
            @RequestBody PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber updatedPhoneNumber = contactService.updatePhoneNumber(contactId, phoneNumberId,
                contactMapper.toPhoneNumber(phoneNumberDTO));
        PhoneNumberDTO updatedPhoneNumberDTO = contactMapper.toPhoneNumberDTO(updatedPhoneNumber);
        return ResponseEntity.ok(updatedPhoneNumberDTO);
    }

    @DeleteMapping("/{contactId}/phoneNumbers/{phoneNumberId}")
    public ResponseEntity<Void> deletePhoneNumber(
            @PathVariable Long contactId,
            @PathVariable Long phoneNumberId) {
        contactService.deletePhoneNumber(contactId, phoneNumberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{contactId}/emails")
    public ResponseEntity<EmailDTO> addEmail(
            @PathVariable Long contactId,
            @RequestBody EmailDTO emailDTO) {
        Email createdEmail = contactService.addEmail(contactId, contactMapper.toEmail(emailDTO));
        EmailDTO createdEmailDTO = contactMapper.toEmailDTO(createdEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmailDTO);
    }

    @PutMapping("/{contactId}/emails/{emailId}")
    public ResponseEntity<EmailDTO> updateEmail(
            @PathVariable Long contactId,
            @PathVariable Long emailId,
            @RequestBody EmailDTO emailDTO) {
        Email updatedEmail = contactService.updateEmail(contactId, emailId, contactMapper.toEmail(emailDTO));
        EmailDTO updatedEmailDTO = contactMapper.toEmailDTO(updatedEmail);
        return ResponseEntity.ok(updatedEmailDTO);
    }


    @DeleteMapping("/{contactId}/emails/{emailId}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long contactId, @PathVariable Long emailId) {
        contactService.deleteEmail(contactId, emailId);
        return ResponseEntity.noContent().build();
    }

}
