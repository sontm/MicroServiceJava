package com.yamastack.hibertest.entity;

import javax.persistence.*;

@Entity
@Table(name = "Gender")
public class Gender {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String type;
    protected Gender() {}

    public Gender(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Gender[id=%d, '%s']",
                id, type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
