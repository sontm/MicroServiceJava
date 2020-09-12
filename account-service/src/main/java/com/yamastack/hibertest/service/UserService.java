package com.yamastack.hibertest.service;

import com.yamastack.hibertest.dao.AccountRepository;
import com.yamastack.hibertest.dao.UserRepository;
import com.yamastack.hibertest.dto.REQSignupUserDTO;
import com.yamastack.hibertest.entity.Account;
import com.yamastack.hibertest.entity.Role;
import com.yamastack.hibertest.entity.User;
import com.yamastack.hibertest.utils.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    UserRepository dao;

    @Autowired
    AccountRepository daoAccount;

    @Override
    @Transactional
    public User getUser(String email, String password) {
        Optional<Account> opt = daoAccount.findByEmailWithType(email, AppConstants.ACCOUNT_TYPE_LOCAL);
        if (opt.isPresent()) {
            // Compare Password
            Account a = opt.get();
            if (passwordEncoder.matches(password, a.getPassword())) {
                // Matched
                Optional<User> optUser = dao.findById(a.getUserId());
                if (opt.isPresent()) {
                    return optUser.get();
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean addUser(REQSignupUserDTO info, String passwordHash) {
        Role role = new Role();
        role.type = AppConstants.ACCOUNT_TYPE_LOCAL;

        User user = new User();
        user.setId(AppConstants.generateRandomUUIDString());
        user.setEmail(info.getEmail());
        user.setFirstName(info.getFirstName());
        user.setMiddleName(info.getMiddleName());
        user.setLastName(info.getLastName());
        user.setPhone(info.getPhone());
        user.setRole(role);

        Account account = new Account();
        account.setType(AppConstants.ACCOUNT_TYPE_LOCAL);
        account.setId(AppConstants.generateRandomUUIDString());
        account.setEmail(info.getEmail());
        account.setPasswordRaw(info.getPassword());
        account.setPassword(passwordHash);
        account.setUserId(user.getId());
        
        if ( dao.save(user) != null) {
            if (daoAccount.save(account) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return (List<User>)dao.findAll();
    }

    
    
}
