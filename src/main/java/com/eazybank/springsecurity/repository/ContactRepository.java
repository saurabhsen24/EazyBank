package com.eazybank.springsecurity.repository;

import com.eazybank.springsecurity.entities.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
