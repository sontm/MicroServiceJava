package com.yamastack.hibertest.service;

import java.util.List;

import com.yamastack.hibertest.dto.REQSignupUserDTO;
import com.yamastack.hibertest.entity.User;


public interface IUserService {
    public User getUser(String username, String passwordHash);
    public boolean addUser(REQSignupUserDTO info, String passwordHash);
    public List<User> getAllUsers();
}
