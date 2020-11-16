package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.MedicationPlan;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Medication plan dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationPlanDto {

    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Patient.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BareUserDto
            patient;

    /**
     * The Period start.
     */
    private Date
            periodStart;

    /**
     * The Period end.
     */
    private Date
            periodEnd;

    /**
     * The Medications.
     */
    private List<MedicationPlanDetailsDto>
            medications;

    /**
     * Instantiates a new Medication plan dto.
     *
     * @param medicationPlan the medication plan
     */
    public MedicationPlanDto(final MedicationPlan medicationPlan) {
        this.id =
                medicationPlan.getId();
        this.patient =
                new BareUserDto(medicationPlan.getPatient());
        this.periodStart =
                medicationPlan.getPeriodStart();
        this.periodEnd =
                medicationPlan.getPeriodEnd();
        this.medications =
                medicationPlan.getMedicationPlanDetailsSet().stream()
                        .map(MedicationPlanDetailsDto::new)
                        .collect(Collectors.toList());
    }
}
