package com.ds.project.apothecary.security.services;

import com.ds.project.apothecary.entities.User;
import com.ds.project.apothecary.repositories.UserRepository;
import com.ds.project.apothecary.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User details service.
 */
@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    /**
     * The User repository.
     */
    private final UserRepository
            userRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param pUserRepository the user repository
     */
    public UserDetailsServiceImpl(final UserRepository pUserRepository) {
        this.userRepository =
                pUserRepository;
    }

    /**
     * Load user by username user details.
     *
     * @param username the username
     * @return the user details
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        User
                user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "User Not Found"
                                                        + " with username: "
                                                        + username));

        return UserDetailsImpl.build(user);
    }
}
