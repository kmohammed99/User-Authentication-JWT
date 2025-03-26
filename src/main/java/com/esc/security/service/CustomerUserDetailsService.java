package com.esc.security.service;

import com.esc.security.dao.UserDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public CustomerUserDetailsService(@Qualifier("JPA") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDAO.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email : " + email + " not found"));
    }
}
