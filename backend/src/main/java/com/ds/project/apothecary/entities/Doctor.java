package com.ds.project.apothecary.entities;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

/**
 * The type Doctor.
 */
@Entity
@SuperBuilder
@NoArgsConstructor
public class Doctor
        extends User {
}
