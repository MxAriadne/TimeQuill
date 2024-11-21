package com.hedgehog.timequill.config;

import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.UserRepository;
import com.hedgehog.timequill.services.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private DBUserService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Load user by username
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(username);

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            throw new BadCredentialsException("Invalid username or password");
        }

        // Create authentication token
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
