package com.yamastack.hibertest.service;

import com.yamastack.hibertest.dao.PersonRepository;
import com.yamastack.hibertest.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository repoPerson;

    @Transactional
    public List<Cart> getAllPersons() {
        return (List<Cart>) repoPerson.findAll();
    }
    @Transactional
    public Cart getById(Long id) {
        Optional<Cart> opt = repoPerson.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    @Transactional
    public boolean addPerson(Cart person) {
        return repoPerson.save(person) != null;
    }

}
