package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Caregiver repository.
 */
@Repository
public interface CaregiverRepository
        extends JpaRepository<Caregiver, Long> {
}
