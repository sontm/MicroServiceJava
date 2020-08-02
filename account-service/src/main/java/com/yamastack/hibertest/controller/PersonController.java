package com.yamastack.hibertest.controller;

import com.yamastack.hibertest.dto.NotificationRequestDTO;
import com.yamastack.hibertest.utils.QueueProducer;
import com.yamastack.hibertest.entity.Person;
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
    private QueueProducer producer;

    @Autowired
    PersonService servicePerson;

    private int count = 1;

    @GetMapping("/persons")
    public List<Person> getAll() throws Exception {
        NotificationRequestDTO dto = new NotificationRequestDTO();
        dto.setCount(count);
        dto.setMessage("Direct Msg");
        dto.setColor("red");
        count++;

        producer.produceDirect(dto);
        System.out.println("Send Producing.");
        List<Person> ret =  servicePerson.getAllPersons();
        ret.add(new Person("a", "b", "c"));
        return ret;
    }
    @PostMapping("/persons")
    public HttpStatus addPerson(@RequestBody Person person) {
        return servicePerson.addPerson(person) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }
}
