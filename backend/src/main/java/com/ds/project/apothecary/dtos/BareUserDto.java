package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Bare user dto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BareUserDto implements Serializable {

    private static final long serialVersionUID = -3988041830383820429L;
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
     * Instantiates a new Bare user dto.
     *
     * @param user the user
     */
    public BareUserDto(final User user) {
        this.id =
                user.getId();
        this.username =
                user.getUsername();
        this.firstName =
                user.getFirstName();
        this.lastName =
                user.getLastName();
    }
}
