package com.raptor.agendadecontatos.controller;

import com.raptor.agendadecontatos.model.Contact;
import com.raptor.agendadecontatos.service.ContactService;
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
    public List<Contact> getAllContacts() {
        return contactService.findAll();
    }

    @GetMapping("contacts/{id}")
    public Contact getContactById(@PathVariable int id) {
        return contactService.findById(id);
    }

    @PostMapping("contact")
    public void addContact(@RequestBody Contact contact) {
        contactService.save(contact);
    }

    @PutMapping("contact")
    public void updateContact(@RequestBody Contact contact) {
        contactService.save(contact);
    }

}
