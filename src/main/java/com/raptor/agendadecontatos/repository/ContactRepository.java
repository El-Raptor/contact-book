package com.raptor.agendadecontatos.repository;


import com.raptor.agendadecontatos.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {

}
