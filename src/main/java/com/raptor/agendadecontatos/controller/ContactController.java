package com.raptor.agendadecontatos.controller;

import com.raptor.agendadecontatos.model.Contact;
import com.raptor.agendadecontatos.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        var contacts = contactService.findAll();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        var contact = contactService.findById(id);
        if (contact == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        contactService.save(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @PutMapping("contacts")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        contactService.save(contact);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

}
