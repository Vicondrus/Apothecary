package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Medication dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicationDto {

    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Name.
     */
    private String
            name;

    /**
     * The Side effects.
     */
    private String
            sideEffects;

    /**
     * The Dosage.
     */
    private Double
            dosage;

    /**
     * The Dosage units.
     */
    private String
            dosageUnits;

    /**
     * Instantiates a new Medication dto.
     *
     * @param medication the medication
     */
    public MedicationDto(final Medication medication) {
        this.id =
                medication.getId();
        this.name =
                medication.getName();
        this.sideEffects =
                medication.getSideEffects();
        this.dosage =
                medication.getDosage();
        this.dosageUnits =
                medication.getDosageUnits();
    }
}
