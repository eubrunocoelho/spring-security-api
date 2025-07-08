package com.eubrunocoelho.spring_security_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users createUser(Users user) {
        // Sets the access date at the time of creation
        user.setAccessTime(new Date());

        return usersRepository.save(user);
    }
}
