package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Patient dto.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Data
public class PatientDto
        extends UserDto {

    /**
     * The Medical record.
     */
    private MedicalRecordDto
            medicalRecord;

    /**
     * The Caregiver.
     */
    private CaregiverDto
            caregiver;

    /**
     * Instantiates a new Patient dto.
     *
     * @param patient the patient
     */
    public PatientDto(final Patient patient) {
        super(patient);
        this.medicalRecord =
                patient.getMedicalRecord() == null ? null
                        : new MedicalRecordDto(patient.getMedicalRecord());
        this.caregiver =
                patient.getCaregiver() == null ? null
                        : new CaregiverDto(patient.getCaregiver());
    }
}
