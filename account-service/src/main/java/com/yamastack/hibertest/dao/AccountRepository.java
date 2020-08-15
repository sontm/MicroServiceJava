package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {
    <S extends Account> S save(S s);
    <S extends Account> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<Account> findById(UUID id);
    boolean existsById(UUID id);
    Iterable<Account> findAll();
    Iterable<Account> findAllById(Iterable<UUID> iterable);
    
    @Query("SELECT u FROM Account u WHERE u.email = ?1 and u.type = ?2")
    Optional<Account> findByEmailWithType(String param, String type);

    long count();
    void deleteById(Long aLong);
    void delete(Account person);
    void deleteAll(Iterable<? extends Account> iterable);
    void deleteAll();
    // @Query("UPDATE customer c SET c.firstName = :firstName WHERE c.id = :id")
    // Integer setNewFirstNameForId(@Param("firstName") String firstName, @Param("id") long id);   
    // }
}
