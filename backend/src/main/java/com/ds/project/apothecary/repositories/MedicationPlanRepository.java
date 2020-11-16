package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.MedicationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Medication plan repository.
 */
@Repository
public interface MedicationPlanRepository
        extends JpaRepository<MedicationPlan, Long> {

    /**
     * Gets all by patient id.
     *
     * @param id the id
     * @return the all by patient id
     */
    List<MedicationPlan> getAllByPatientId(Long id);
}
