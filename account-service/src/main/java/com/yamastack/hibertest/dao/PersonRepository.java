package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    <S extends Person> S save(S s);
    <S extends Person> Iterable<S> saveAll(Iterable<S> iterable);
    Optional<Person> findById(Long aLong);
    boolean existsById(Long aLong);
    Iterable<Person> findAll();
    Iterable<Person> findAllById(Iterable<Long> iterable);
    long count();
    void deleteById(Long aLong);
    void delete(Person person);
    void deleteAll(Iterable<? extends Person> iterable);
    void deleteAll();
}
