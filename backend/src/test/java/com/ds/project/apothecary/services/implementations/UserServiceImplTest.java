package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Address service impl test.
 */
public class UserServiceImplTest {

    /**
     * The User repository.
     */
    private UserRepository
            userRepository;

    /**
     * The Password encoder.
     */
    private PasswordEncoder
            passwordEncoder;

    /**
     * The User service.
     */
    private UserServiceImpl
            userService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        userRepository =
                Mockito.mock(UserRepository.class);
        passwordEncoder =
                Mockito.mock(PasswordEncoder.class);
        userService =
                new UserServiceImpl(userRepository, passwordEncoder);
    }

    /**
     * Find all test.
     */
    @Test
    void existsByUsernameTest() {
        Mockito.when(userRepository.existsByUsername("user"))
                .thenReturn(Boolean.TRUE);

        Assertions.assertTrue(userService.existsByUsername("user"));
    }
}
