package com.raptor.agendadecontatos.repository;


import com.raptor.agendadecontatos.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact,Integer> {

}
