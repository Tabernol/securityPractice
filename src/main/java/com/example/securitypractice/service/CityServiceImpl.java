//package com.example.securitypractice.service;
//
//import com.example.securitypractice.database.entity.City;
//import com.example.securitypractice.database.entity.User;
//import com.example.securitypractice.database.repository.CityRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class CityServiceImpl {
//    private final CityRepo cityRepo;
//
//    public List<City> findAll() {
//        List<City> cityList = new ArrayList<>();
//        for (City city : cityRepo.findAll()) {
//            cityList.add(city);
//        }
//        return cityList;
//    }
//}
