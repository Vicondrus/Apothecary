package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Medication dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicationDto implements Serializable {

    private static final long serialVersionUID = 506589207669100153L;

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
}
