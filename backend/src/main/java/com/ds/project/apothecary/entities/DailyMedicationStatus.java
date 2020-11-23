package com.ds.project.apothecary.entities;

import com.ds.project.apothecary.enums.MedicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * The type Daily medication status.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyMedicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The Medication.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "medication_plan_details_id",
            nullable = false)
    private MedicationPlanDetails
            medicationPlanDetails;

    private MedicationStatus status;

    private Date date;
}
