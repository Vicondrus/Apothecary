package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Medical record repository.
 */
@Repository
public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {
}
