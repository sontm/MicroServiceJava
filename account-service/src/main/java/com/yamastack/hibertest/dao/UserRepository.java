package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // <S extends User> S save(S s);
    // <S extends User> Iterable<S> saveAll(Iterable<S> iterable);

    // Optional<User> findById(UUID id);
    // boolean existsById(UUID id);
    // Iterable<User> findAll();
    // Iterable<User> findAllById(Iterable<UUID> iterable);
    
    // long count();
    // void deleteById(Long aLong);
    // void delete(User person);
    // void deleteAll(Iterable<? extends User> iterable);
    // void deleteAll();
    // @Query("UPDATE customer c SET c.firstName = :firstName WHERE c.id = :id")
    // Integer setNewFirstNameForId(@Param("firstName") String firstName, @Param("id") long id);   
    // }
}
