package com.yamastack.hibertest.controller;

import com.yamastack.hibertest.dto.REQAuthenticateDTO;
import com.yamastack.hibertest.dto.REQRoleDTO;
import com.yamastack.hibertest.dto.REQSignupUserDTO;
import com.yamastack.hibertest.entity.User;
import com.yamastack.hibertest.service.RoleService;
import com.yamastack.hibertest.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    RoleService serviceRole;

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/roles")
    public ResponseEntity<?> addRole(@RequestBody REQRoleDTO req) {
        logger.debug("[Account] signup ");
        if (serviceRole.addRole(req)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        logger.debug("[Account] getAllRoles ");
        return ResponseEntity.ok(serviceRole.getAllRoles());
    }
}
