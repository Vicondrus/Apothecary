package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.UserDto;
import com.ds.project.apothecary.entities.User;
import com.ds.project.apothecary.repositories.UserRepository;
import com.ds.project.apothecary.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl
        implements UserService {

    /**
     * The User repository.
     */
    private final UserRepository
            userRepository;

    /**
     * The Encoder.
     */
    private final PasswordEncoder
            encoder;

    /**
     * Instantiates a new User service.
     *
     * @param pUserRepository the user repository
     * @param pEncoder        the encoder
     */
    public UserServiceImpl(final UserRepository pUserRepository,
                           final PasswordEncoder pEncoder) {
        this.userRepository =
                pUserRepository;
        this.encoder =
                pEncoder;
    }

    /**
     * Create user dto.
     *
     * @param userDto the user dto
     * @return the user dto
     */
    @Override
    public UserDto create(final UserDto userDto) {
        User
                user =
                User.builder()
                        .username(userDto.getUsername())
                        .password(encoder.encode(userDto.getPassword()))
                        .build();
        return new UserDto(userRepository.save(user));
    }

    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    @Override
    public Boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }
}
