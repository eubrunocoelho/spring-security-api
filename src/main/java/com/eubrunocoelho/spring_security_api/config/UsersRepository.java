package com.eubrunocoelho.spring_security_api.config;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    public List<Users> findByUserName(String userName);
}
