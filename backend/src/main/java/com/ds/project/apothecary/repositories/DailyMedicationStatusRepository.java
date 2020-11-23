package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.DailyMedicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMedicationStatusRepository extends
        JpaRepository<DailyMedicationStatus, Long> {
}
