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
import java.util.Date;
import java.util.Set;

/**
 * The type Medication plan.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationPlan {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long
            id;

    /**
     * The Patient.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "patient_id",
            nullable = false)
    private Patient
            patient;

    /**
     * The Medication plan details set.
     */
    @OneToMany(mappedBy = "medicationPlan",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<MedicationPlanDetails>
            medicationPlanDetailsSet;

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
}
