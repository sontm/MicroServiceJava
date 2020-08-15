package com.yamastack.hibertest.service;

import com.yamastack.hibertest.dao.AccountRepository;
import com.yamastack.hibertest.dao.RoleRepository;
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

    @Autowired
    RoleRepository daoRole;

    @Override
    @Transactional
    public User getUser(String email, String password) {
        Optional<Account> opt = daoAccount.findByEmailWithType(email, AppConstants.ACCOUNT_TYPE_LOCAL);
        if (opt.isPresent()) {
            // Compare Password
            Account a = opt.get();
            if (passwordEncoder.matches(password, a.getPassword())) {
                // Matched
                return a.getUser();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean addUser(REQSignupUserDTO info, String passwordHash) {
        Optional<Role> optRole = daoRole.findByRoleType(AppConstants.ROLE_NORMAL);
        if (optRole.isPresent()) {
            Role role = optRole.get();
            User user = new User();
            user.setId(AppConstants.generateRandomUUID());
            user.setEmail(info.getEmail());
            user.setFirstName(info.getFirstName());
            user.setMiddleName(info.getMiddleName());
            user.setLastName(info.getLastName());
            user.setPhone(info.getPhone());
            user.setRole(role);

            Account account = new Account();
            account.setType(AppConstants.ACCOUNT_TYPE_LOCAL);
            account.setId(AppConstants.generateRandomUUID());
            account.setEmail(info.getEmail());
            account.setPasswordRaw(info.getPassword());
            account.setPassword(passwordHash);
            account.setUser(user);
            
            if ( daoRole.save(role) != null) {
                if ( dao.save(user) != null) {
                    if (daoAccount.save(account) != null) {
                        return true;
                    }
                }
                
            }
        }
        return false;
    }
    
}
