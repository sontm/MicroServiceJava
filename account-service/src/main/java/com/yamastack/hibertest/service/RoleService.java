package com.yamastack.hibertest.service;

import com.yamastack.hibertest.dao.AccountRepository;
import com.yamastack.hibertest.dao.RoleRepository;
import com.yamastack.hibertest.dao.UserRepository;
import com.yamastack.hibertest.dto.REQRoleDTO;
import com.yamastack.hibertest.dto.REQSignupUserDTO;
import com.yamastack.hibertest.entity.Account;
import com.yamastack.hibertest.entity.Role;
import com.yamastack.hibertest.entity.User;
import com.yamastack.hibertest.utils.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    RoleRepository daoRole;

    @Override
    @Transactional
    public Role getRole(String type) {
        Optional<Role> role = daoRole.findByRoleType(type);
        if (role.isPresent()) {
            return role.get();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean addRole(REQRoleDTO info) {
        Role role = new Role(AppConstants.generateRandomUUID(), info.getType());

        if ( daoRole.save(role) != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return (List<Role>)daoRole.findAll();
    }

    
    
    
}
