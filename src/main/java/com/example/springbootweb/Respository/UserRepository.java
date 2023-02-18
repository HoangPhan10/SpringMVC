package com.example.springbootweb.Respository;

import com.example.springbootweb.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsernameAndPassword(String username,String password);
    User findUserByUsername(String username);
}
