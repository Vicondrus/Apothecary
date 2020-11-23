package com.ds.project.apothecary.entities;

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
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * The type Medication plan details.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationPlanDetails {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long
            id;

    /**
     * The Medication.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "medication_id",
            nullable = false)
    private Medication
            medication;

    /**
     * The Medication plan.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "medication_plan_id",
            nullable = false)
    private MedicationPlan
            medicationPlan;

    /**
     * The Intake interval.
     */
    private Integer
            intakeInterval;

    private Integer intakeIntervalEnd;

    @OneToMany(mappedBy = "medicationPlanDetails",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<DailyMedicationStatus>
            dailyMedicationStatuses;
}
