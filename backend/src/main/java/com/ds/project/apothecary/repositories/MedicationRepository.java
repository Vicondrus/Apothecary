package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Medication repository.
 */
@Repository
public interface MedicationRepository
        extends JpaRepository<Medication, Long> {

    /**
     * Find all by name containing ignore case list.
     *
     * @param name the name
     * @return the list
     */
    List<Medication> findAllByNameContainingIgnoreCase(String name);
}
