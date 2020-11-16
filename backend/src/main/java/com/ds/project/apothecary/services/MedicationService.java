package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.MedicationDto;

import java.util.List;

/**
 * The interface Medication service.
 */
public interface MedicationService
        extends CrudService<MedicationDto> {

    /**
     * Find all by name like list.
     *
     * @param name the name
     * @return the list
     */
    List<MedicationDto> findAllByNameLike(String name);
}
