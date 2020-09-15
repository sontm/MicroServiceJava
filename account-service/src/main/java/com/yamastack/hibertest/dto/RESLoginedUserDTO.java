package com.yamastack.hibertest.dto;

import java.util.UUID;

import com.yamastack.hibertest.entity.User;




public class RESLoginedUserDTO {
    private User user;
    private String jwt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    

}
