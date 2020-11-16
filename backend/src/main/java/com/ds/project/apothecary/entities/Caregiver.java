package com.ds.project.apothecary.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * The type Caregiver.
 */
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Caregiver
        extends User {

    /**
     * The Patients.
     */
    @OneToMany(mappedBy = "caregiver",
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    private Set<Patient>
            patients;
}
