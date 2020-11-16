package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Login dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDto {

    /**
     * The Username.
     */
    private String
            username;

    /**
     * The Password.
     */
    private String
            password;
}
