package com.ds.project.apothecary.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The type Medication plan dto.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicationPlanDto implements Serializable {
    private static final long serialVersionUID = 3445194553536051405L;

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

    public void addMedicationPlanDetailsToList(
            MedicationPlanDetailsDto medicationPlanDetailsDto) {
        this.medications.add(medicationPlanDetailsDto);
    }
}
