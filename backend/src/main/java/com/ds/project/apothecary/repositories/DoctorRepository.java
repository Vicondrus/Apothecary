package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Doctor repository.
 */
@Repository
public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {
}
