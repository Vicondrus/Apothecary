package com.ds.project.apothecary.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

/**
 * The type Patient.
 */
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Patient
        extends User {

    /**
     * The Caregiver.
     */
    @ManyToOne(fetch = FetchType.EAGER,
            optional = true)
    @JoinColumn(name = "caregiver_id")
    private Caregiver
            caregiver;

    /**
     * The Medical record.
     */
    @OneToOne(mappedBy = "patient",
            cascade = CascadeType.ALL)
    private MedicalRecord
            medicalRecord;

    /**
     * The Medication plans.
     */
    @OneToMany(
            mappedBy = "patient",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE})
    private Set<MedicationPlan>
            medicationPlans;

    @OneToMany(mappedBy = "patient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<AnomalousActivity>
            anomalousActivities;
}
