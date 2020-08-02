package com.yamastack.hibertest.controller;

import com.yamastack.hibertest.entity.Cart;
import com.yamastack.hibertest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService servicePerson;

    @GetMapping("/carts")
    public List<Cart> getAll() {
        List<Cart> ret =  servicePerson.getAllPersons();
        return ret;
    }
    @PostMapping("/carts")
    public HttpStatus addPerson(@RequestBody Cart person) {
        return servicePerson.addPerson(person) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }
}
