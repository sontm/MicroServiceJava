package com.yamastack.hibertest.dao;

import com.yamastack.hibertest.entity.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    <S extends Role> S save(S s);
    <S extends Role> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<Role> findById(UUID id);
    boolean existsById(UUID id);
    Iterable<Role> findAll();
    Iterable<Role> findAllById(Iterable<UUID> iterable);
    @Query("SELECT u FROM Role u WHERE u.type = ?1")
    Optional<Role> findByRoleType(String type);

    long count();
    void deleteById(Long aLong);
    void delete(Role person);
    void deleteAll(Iterable<? extends Role> iterable);
    void deleteAll();
}
