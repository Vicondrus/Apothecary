package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.Caregiver;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The type Caregiver dto.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
@Data
public class CaregiverDto
        extends UserDto {

    /**
     * Instantiates a new Caregiver dto.
     *
     * @param caregiver the caregiver
     */
    public CaregiverDto(final Caregiver caregiver) {
        super(caregiver);
    }
}
