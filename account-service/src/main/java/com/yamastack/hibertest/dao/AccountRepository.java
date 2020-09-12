package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query("{email:'?0', type:'?1'}")
    Optional<Account> findByEmailWithType(String param, String type);

    // findAll
    // findOne(id)
    // save(entity)
}
