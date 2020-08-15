package com.yamastack.hibertest.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "role", schema = "public")
public class Role {
    @Id
    private UUID id;

    @Column(nullable = false, length = 20)
    private String type; // "Normal", "Admin"
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @JsonManagedReference
    private Set<User> users = new HashSet<User>(0);
            

    protected Role() {}

    public Role(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    // Auto-Generate Random UUID
    public Role(String type) {
        this.id = java.util.UUID.randomUUID();
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    
    
}
