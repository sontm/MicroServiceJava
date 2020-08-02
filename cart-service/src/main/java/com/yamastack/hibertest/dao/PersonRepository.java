package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Cart, Long> {
    <S extends Cart> S save(S s);
    <S extends Cart> Iterable<S> saveAll(Iterable<S> iterable);
    Optional<Cart> findById(Long aLong);
    boolean existsById(Long aLong);
    Iterable<Cart> findAll();
    Iterable<Cart> findAllById(Iterable<Long> iterable);
    long count();
    void deleteById(Long aLong);
    void delete(Cart person);
    void deleteAll(Iterable<? extends Cart> iterable);
    void deleteAll();
}
