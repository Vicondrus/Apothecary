package com.ds.project.apothecary.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * The type Medical record.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long
            id;

    /**
     * The Description.
     */
    private String
            description;

    /**
     * The Patient.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id",
            referencedColumnName = "id")
    private Patient
            patient;
}
