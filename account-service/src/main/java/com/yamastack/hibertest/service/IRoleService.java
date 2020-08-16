package com.yamastack.hibertest.service;

import java.util.List;

import com.yamastack.hibertest.dto.REQRoleDTO;
import com.yamastack.hibertest.entity.Role;


public interface IRoleService {
    public Role getRole(String type);
    public boolean addRole(REQRoleDTO info);
    public List<Role> getAllRoles();
}
