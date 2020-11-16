package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.Doctor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Doctor dto.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Data
public class DoctorDto
        extends UserDto {

    /**
     * Instantiates a new Doctor dto.
     *
     * @param doctor the doctor
     */
    public DoctorDto(final Doctor doctor) {
        super(doctor);
    }
}
