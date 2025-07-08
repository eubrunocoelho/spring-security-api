package com.eubrunocoelho.spring_security_api;

import com.eubrunocoelho.spring_security_api.config.UsersService;
import com.eubrunocoelho.spring_security_api.config.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        Users createdUser = usersService.createUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
