package com.eubrunocoelho.spring_security_api;

import com.eubrunocoelho.spring_security_api.config.LoginRequest;
import com.eubrunocoelho.spring_security_api.config.UsersService;
import com.eubrunocoelho.spring_security_api.config.Users;
import com.eubrunocoelho.spring_security_api.jwt.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        Users createdUser = usersService.createUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Users user = usersService.getUserByUserName(loginRequest.getUserName());

        if (user == null || !user.getPassword().equalsIgnoreCase(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Token creation
        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String token = jwtUtility.generateToken(claims, user.getUserName(), 1000 * 60 * 60 * 24); // 1 day

        // Return header "Authorization: Bearer <token>"
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(Map.of(
                        "token", token,
                        "username", user.getUserName(),
                        "role", user.getRole().name()
                ));
    }
}
