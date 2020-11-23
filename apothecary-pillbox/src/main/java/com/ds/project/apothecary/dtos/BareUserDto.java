package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
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
}
