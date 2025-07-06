package com.eubrunocoelho.spring_security_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoginUtilityService {

    @Autowired
    UsersRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthenticadedUser findMatch(String username) {
        List<Users> users = usersRepository.findByUserName(username);

        if (users == null || users.size() == 0) {
            logger.info("User not found: " + username);

            throw new UsernameNotFoundException("UserName " + username + " doesn't exists");
        }

        Users user = users.get(0);

        logger.info("User Instance: " + user);

        // For multiple content level access, use content-level permissions for each role
        // Mapping: USER entity - (1-n) Role entity - (1-n) Permission entity
        GrantedAuthority authority = () -> {
            return user.getRole().name();
        };

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(Arrays.asList(authority));

        return new AuthenticadedUser(user, authorities);
    }

    // Get currently logged in user's entity instance (for business logic)
    public Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null)
            return null;

        return ((AuthenticadedUser) authentication.getPrincipal()).getUser();
    }
}
