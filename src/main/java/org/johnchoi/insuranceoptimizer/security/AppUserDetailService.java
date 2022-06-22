package org.johnchoi.insuranceoptimizer.security;


import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.User;

import org.johnchoi.insuranceoptimizer.services.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserDetailService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public AppUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = this.userService.findUserByEmail(email);
            return new AppUserDetails(user, getAuthorities((user)));
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = {user.getUserRole().name()};
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
