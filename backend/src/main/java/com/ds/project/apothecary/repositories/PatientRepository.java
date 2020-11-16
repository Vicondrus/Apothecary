package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Patient repository.
 */
@Repository
public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    /**
     * Find all by caregiver id list.
     *
     * @param id the id
     * @return the list
     */
    List<Patient> findAllByCaregiverId(Long id);
}
