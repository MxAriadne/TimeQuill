package com.hedgehog.timequill.services;

import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * The goal of this class is to overwrite the SpringSecurity user data service and implement our own
 * using the database.
 */
@Service
public class DBUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = null;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not present");
        }
        return user;
    }



    public void createUser(UserDetails user) {
        userRepository.save((UserEntity) user);
    }
}
