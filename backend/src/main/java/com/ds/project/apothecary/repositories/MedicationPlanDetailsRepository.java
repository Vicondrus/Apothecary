package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.MedicationPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationPlanDetailsRepository extends
        JpaRepository<MedicationPlanDetails, Long> {
}
