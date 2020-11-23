package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Medication plan details dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicationPlanDetailsDto implements Serializable {
    private static final long serialVersionUID = 5668298128781121469L;

    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Medication.
     */
    private MedicationDto
            medication;

    /**
     * The Intake interval.
     */
    private Integer
            intakeInterval;

    private Integer intakeIntervalEnd;

    @Override public String toString() {
        return this.medication.getName() + " between " + this.intakeInterval +
                " and " +
                this.intakeIntervalEnd + " hours";
    }
}
