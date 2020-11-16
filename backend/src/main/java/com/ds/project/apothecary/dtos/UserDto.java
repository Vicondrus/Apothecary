package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.User;
import com.ds.project.apothecary.enums.Gender;
import com.ds.project.apothecary.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type User dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UserDto {

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
     * The Password.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String
            password;

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
     * The Birth date.
     */
    private Date
            birthDate;

    /**
     * The Gender.
     */
    private Gender
            gender;

    /**
     * The User type.
     */
    private UserType
            userType;

    /**
     * The Address.
     */
    private AddressDto
            address;

    /**
     * Instantiates a new User dto.
     *
     * @param user the user
     */
    public UserDto(final User user) {
        this.id =
                user.getId();
        this.username =
                user.getUsername();
        this.password =
                user.getPassword();
        this.firstName =
                user.getFirstName();
        this.lastName =
                user.getLastName();
        this.birthDate =
                user.getBirthDate();
        this.gender =
                user.getGender();
        this.userType =
                user.getUserType();
        this.address =
                new AddressDto(user.getAddress());
    }
}
