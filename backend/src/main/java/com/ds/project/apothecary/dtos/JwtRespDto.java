package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Jwt resp dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRespDto {

    /**
     * The Access token.
     */
    private String
            accessToken;

    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Username.
     */
    private String
            username;

    /**
     * The First name.
     */
    private String
            firstName;

    /**
     * The Last name.
     */
    private String
            lastName;

    /**
     * The Roles.
     */
    private List<String>
            roles;
}
