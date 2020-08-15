package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    <S extends User> S save(S s);
    <S extends User> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<User> findById(UUID id);
    boolean existsById(UUID id);
    Iterable<User> findAll();
    Iterable<User> findAllById(Iterable<UUID> iterable);
    
    long count();
    void deleteById(Long aLong);
    void delete(User person);
    void deleteAll(Iterable<? extends User> iterable);
    void deleteAll();
    // @Query("UPDATE customer c SET c.firstName = :firstName WHERE c.id = :id")
    // Integer setNewFirstNameForId(@Param("firstName") String firstName, @Param("id") long id);   
    // }
}
