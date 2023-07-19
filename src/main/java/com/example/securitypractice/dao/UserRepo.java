package com.example.securitypractice.dao;

import com.example.securitypractice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
}
