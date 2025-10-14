package com.raptor.agendadecontatos.service;

import com.raptor.agendadecontatos.model.Contact;
import com.raptor.agendadecontatos.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepo;

    public ContactService(ContactRepository contactRepo) {
        this.contactRepo = contactRepo;
    }

    public List<Contact> findAll() {
        return (List<Contact>) contactRepo.findAll();
    }

    public Contact findById(Integer id) {
        return contactRepo.findById(id).orElse(null);
    }

    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }

    public void deleteById(Integer id) {
        contactRepo.deleteById(id);
    }
}
