package com.agh.givealift.security;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.err.println("<<<"+username+">>>");
        GalUser user = userService.getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        System.err.println("<<<"+user+">>>");
        return new UserDetails(user);
    }
}