package com.eubrunocoelho.spring_security_api.config;

import java.util.Date;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class Users {

    public static enum Role {
        ROLE_ADMIN, ROLE_MANAGER, ROLE_STAFF;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;

    //    @Convert(converter = CryptoConverter.class)
    String password;

    Role role;
    Date accessTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", userName=" + userName + ", password=" + password + ", role=" + role
                + ", accessTime=" + accessTime + "]";
    }
}
