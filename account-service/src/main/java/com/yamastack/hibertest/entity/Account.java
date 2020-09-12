package com.yamastack.hibertest.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;

public class Account {
    @Id
    private String id;

    private String type; // local, facebook...

    private String email;

    @JsonIgnore
    private String passwordRaw;

    @JsonIgnore
    private String password;

    private String accessToken;

    private String uniqueId;

    private String pictureUrl;

    private String fullName;

    private String userId;

    // --------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    
}
