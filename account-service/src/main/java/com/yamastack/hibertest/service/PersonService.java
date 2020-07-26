package com.yamastack.hibertest.service;

import com.yamastack.hibertest.dao.PersonRepository;
import com.yamastack.hibertest.entity.Person;
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
    public List<Person> getAllPersons() {
        return (List<Person>) repoPerson.findAll();
    }
    @Transactional
    public Person getById(Long id) {
        Optional<Person> opt = repoPerson.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    @Transactional
    public boolean addPerson(Person person) {
        return repoPerson.save(person) != null;
    }

}
