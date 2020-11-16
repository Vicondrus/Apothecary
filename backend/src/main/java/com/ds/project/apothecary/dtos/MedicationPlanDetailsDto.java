package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.MedicationPlanDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Medication plan details dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationPlanDetailsDto {

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

    /**
     * Instantiates a new Medication plan details dto.
     *
     * @param medicationPlanDetails the medication plan details
     */
    public MedicationPlanDetailsDto(
            final MedicationPlanDetails medicationPlanDetails) {
        this.id =
                medicationPlanDetails.getId();
        this.intakeInterval =
                medicationPlanDetails.getIntakeInterval();
        this.medication =
                new MedicationDto(medicationPlanDetails.getMedication());
        this.intakeIntervalEnd = medicationPlanDetails.getIntakeIntervalEnd();
    }
}
