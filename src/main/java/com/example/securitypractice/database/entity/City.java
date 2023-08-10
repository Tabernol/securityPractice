package com.example.securitypractice.database.entity;

import lombok.Data;

@Data
public class City{
    private Long id;
    private String name;
    private Double lat;
    private Double lng;
    private String country;
    private String iso2;
    private String adminName;
    private String capital;
    private Integer population;
    private String population_proper;
}
