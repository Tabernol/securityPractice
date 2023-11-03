package com.example.securitypractice.database.entity;

import com.example.securitypractice.database.entity.recyclable.Recyclable;

import java.sql.Timestamp;
import java.util.Map;

public class UserApplication {
    private Long id;
    private Long userId;
    private Timestamp timeCreated;
    private Location location;
    private Map<Integer, Recyclable> garbage;


}
