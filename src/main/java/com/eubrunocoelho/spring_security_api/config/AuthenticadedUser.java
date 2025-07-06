package com.eubrunocoelho.spring_security_api.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Wrapper class to hold Authenticated User Details and Entity Instance
 */
public class AuthenticadedUser extends User {

    private static final long serialVersionUID = 1L;
    private Users user;

    public AuthenticadedUser(Users user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);

        this.user = user;
    }

    public AuthenticadedUser(
            Users user,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(
                user.getUserName(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
    }

    public Users getUser() {
        return user;
    }
}
