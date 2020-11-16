package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.UserDto;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Create user dto.
     *
     * @param userDto the user dto
     * @return the user dto
     */
    UserDto create(UserDto userDto);

    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    Boolean existsByUsername(String username);
}
