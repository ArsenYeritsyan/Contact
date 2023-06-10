package com.crud.contacts.service;

import com.crud.contacts.exception.NotFoundException;
import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import com.crud.contacts.repo.ContactRepository;
import com.crud.contacts.repo.EmailRepository;
import com.crud.contacts.repo.PhoneNumberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final EmailRepository emailRepository;

    public ContactService(ContactRepository contactRepository, PhoneNumberRepository phoneNumberRepository, EmailRepository emailRepository) {
        this.contactRepository = contactRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.emailRepository = emailRepository;
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findByIdAndDeletedFalse(id);
    }

    public List<Contact> getContactsList(Pageable pageable) {
        return contactRepository.findAllByDeletedFalse(pageable);
    }

    public void deleteContact(Long id) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(id);
        contactOptional.ifPresent(contact -> {
            contact.setDeleted(true);
            contactRepository.save(contact);
        });
    }

    public PhoneNumber addPhoneNumber(Long contactId, PhoneNumber phoneNumber) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        return contactOptional.map(contact -> {
            phoneNumber.setContact(contact);
            return phoneNumberRepository.save(phoneNumber);
        }).orElseThrow(() -> new NotFoundException("Contact not found"));
    }


    public PhoneNumber updatePhoneNumber(Long contactId, Long phoneNumberId, PhoneNumber phoneNumber) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        Optional<PhoneNumber> phoneNumberOptional = phoneNumberRepository.findById(phoneNumberId);
        return contactOptional.flatMap(contact -> phoneNumberOptional.map(existingPhoneNumber -> {
            existingPhoneNumber.setPhoneNumber(phoneNumber.getPhoneNumber());
            existingPhoneNumber.setLabel(phoneNumber.getLabel());
            return phoneNumberRepository.save(existingPhoneNumber);
        })).orElseThrow(() -> new NotFoundException("Contact or Phone Number not found"));
    }

    public void deletePhoneNumber(Long contactId, Long phoneNumberId) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        Optional<PhoneNumber> phoneNumberOptional = phoneNumberRepository.findById(phoneNumberId);
        contactOptional.flatMap(contact -> phoneNumberOptional.map(phoneNumber -> {
            contact.getPhoneNumbers().remove(phoneNumber);
            contactRepository.save(contact);
            phoneNumberRepository.delete(phoneNumber);
            return phoneNumber;
        })).orElseThrow(() -> new NotFoundException("Contact or Phone Number not found"));
    }

    public Email addEmail(Long contactId, Email email) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        return contactOptional.map(contact -> {
            email.setContact(contact);
            return emailRepository.save(email);
        }).orElseThrow(() -> new NotFoundException("Contact not found"));
    }

    public Email updateEmail(Long contactId, Long emailId, Email email) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        Optional<Email> emailOptional = emailRepository.findById(emailId);
        return contactOptional.flatMap(contact -> emailOptional.map(existingEmail -> {
            existingEmail.setEmail(email.getEmail());
            existingEmail.setDomainName(email.getDomainName());
            return emailRepository.save(existingEmail);
        })).orElseThrow(() -> new NotFoundException("Contact or Email not found"));
    }

    public void deleteEmail(Long contactId, Long emailId) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndDeletedFalse(contactId);
        Optional<Email> emailOptional = emailRepository.findById(emailId);
        contactOptional.flatMap(contact -> emailOptional.map(email -> {
            contact.getEmails().remove(email);
            contactRepository.save(contact);
            emailRepository.delete(email);
            return email;
        })).orElseThrow(() -> new NotFoundException("Contact or Email not found"));
    }
    @Cacheable("contacts")
    public List<Contact> searchContacts(Specification<Contact> spec) {
        return contactRepository.findAll(spec);
    }

}
