package com.yamastack.hibertest.entity;

import javax.persistence.*;

@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String prodName;

    protected Cart() {}

    public Cart(String prodName) {
        this.prodName = prodName;
    }

    @Override
    public String toString() {
        return String.format(
                "Cart[id=%d, '%s' ]",
                id, prodName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
