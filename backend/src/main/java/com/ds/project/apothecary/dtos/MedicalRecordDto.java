package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.MedicalRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Medical record dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MedicalRecordDto implements Serializable {

    private static final long serialVersionUID = -3298839755576320671L;
    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Description.
     */
    private String
            description;

    /**
     * Instantiates a new Medical record dto.
     *
     * @param medicalRecord the medical record
     */
    public MedicalRecordDto(final MedicalRecord medicalRecord) {
        this.id =
                medicalRecord.getId();
        this.description =
                medicalRecord.getDescription();
    }
}
