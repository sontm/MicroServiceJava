package com.yamastack.hibertest.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "account", schema = "public")
public class Account {
    @Id
    private UUID id;

    @Column(length = 10)
    private String type; // local, facebook...

    @Column(length = 30)
    private String email;

    @Column(length = 30)
    @JsonIgnore
    private String passwordRaw;
    @Column(length = 100)
    @JsonIgnore
    private String password;

    @Column(length = 50)
    private String accessToken;

    @Column(length = 50)
    private String uniqueId;

    @Column(length = 300)
    private String pictureUrl;

    @Column(length = 100)
    private String fullName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fid_user", nullable = false)
    @JsonBackReference
    private User user;

    // --------------------------------------------------------

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accesToken) {
        this.accessToken = accesToken;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPasswordRaw() {
        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {
        this.passwordRaw = passwordRaw;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
