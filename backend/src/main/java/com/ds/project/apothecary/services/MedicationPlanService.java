package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.MedicationPlanDto;

import java.util.List;

/**
 * The interface Medication plan service.
 */
public interface MedicationPlanService {

    /**
     * Find all for patient id list.
     *
     * @param id the id
     * @return the list
     */
    List<MedicationPlanDto> findAllForPatientId(Long id);

    /**
     * Create medication plan dto.
     *
     * @param patientId         the patient id
     * @param medicationPlanDto the medication plan dto
     * @return the medication plan dto
     */
    MedicationPlanDto create(Long patientId,
                             MedicationPlanDto medicationPlanDto);
}
