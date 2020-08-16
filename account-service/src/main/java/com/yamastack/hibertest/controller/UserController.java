package com.yamastack.hibertest.controller;

import com.yamastack.hibertest.dto.REQAuthenticateDTO;
import com.yamastack.hibertest.dto.REQSignupUserDTO;
import com.yamastack.hibertest.entity.User;
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
public class UserController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Autowired
    UserService service;

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public ResponseEntity<?> index() {
        logger.debug("[Account] Index");
        return ResponseEntity.ok("Welcome to AccountService");
    }

    /**
     * Authenticate with User and Password
     *
     * @param req contain UserName/Email and Password
     * @return User Info and Token
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUserAndPassword(@RequestBody REQAuthenticateDTO req) {
        logger.debug("[Account] Auth with User {}, pwd {}", req.getEmail(), req.getPassword());
        logger.debug("  BCryptPwd:{}", passwordEncoder.encode(req.getPassword()));
        User user = service.getUser(req.getEmail(), req.getPassword());
        if (user != null) {
            logger.debug(" Login OK");
            return ResponseEntity.ok(user);
        } else {
            logger.debug(" Login Failed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody REQSignupUserDTO req) {
        logger.debug("[Account] signup ");
        if (service.addUser(req, passwordEncoder.encode(req.getPassword()))) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        logger.debug("[Account] getAllUsers ");
        return ResponseEntity.ok(service.getAllUsers());
    }
}
