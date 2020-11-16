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
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * The type Medication.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * The Medication plan details set.
     */
    @OneToMany(mappedBy = "medication",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<MedicationPlanDetails>
            medicationPlanDetailsSet;
}
