package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.PatientDto;

import java.util.List;

/**
 * The interface Patient service.
 */
public interface PatientService
        extends CrudService<PatientDto> {

    /**
     * Find all by caregiver id list.
     *
     * @param id the id
     * @return the list
     */
    List<PatientDto> findAllByCaregiverId(Long id);
}
