package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.MedicationDto;
import com.ds.project.apothecary.services.MedicationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Medication controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/medications")
public class MedicationController {

    /**
     * The Medication service.
     */
    private final MedicationService
            medicationService;

    /**
     * Instantiates a new Medication controller.
     *
     * @param pMedicationService the medication service
     */
    public MedicationController(final MedicationService pMedicationService) {
        this.medicationService =
                pMedicationService;
    }

    /**
     * Gets medication by name.
     *
     * @param name the name
     * @return the medication by name
     */
    @GetMapping
    public List<MedicationDto> getMedicationByName(final String name) {
        if (name != null) {
            return medicationService.findAllByNameLike(name);
        } else {
            return medicationService.findAll();
        }
    }

    /**
     * Create medication medication dto.
     *
     * @param medicationDto the medication dto
     * @return the medication dto
     */
    @PostMapping
    public MedicationDto createMedication(
            @RequestBody final MedicationDto medicationDto) {
        return medicationService.create(medicationDto);
    }

    /**
     * Gets medication.
     *
     * @param id the id
     * @return the medication
     */
    @GetMapping("/{id}")
    public MedicationDto getMedication(
            @PathVariable final Long id) {
        return medicationService.findById(id);
    }

    /**
     * Update medication medication dto.
     *
     * @param id            the id
     * @param medicationDto the medication dto
     * @return the medication dto
     */
    @PutMapping("/{id}")
    public MedicationDto updateMedication(
            @PathVariable final Long id,
            @RequestBody final MedicationDto medicationDto) {
        return medicationService.update(id, medicationDto);
    }

    /**
     * Delete medication boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/{id}")
    public Boolean deleteMedication(
            @PathVariable final Long id) {
        return medicationService.delete(id);
    }
}
