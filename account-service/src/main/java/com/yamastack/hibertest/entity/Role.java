package com.yamastack.hibertest.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.annotation.Id;

public class Role {
    public String type; // "Normal", "Admin"
}
