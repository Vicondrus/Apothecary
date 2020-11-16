package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.CaregiverDto;
import com.ds.project.apothecary.services.CaregiverService;
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
 * The type Caregiver controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/caregivers")
public class CaregiverController {

    /**
     * The Caregiver service.
     */
    private final CaregiverService
            caregiverService;

    /**
     * Instantiates a new Caregiver controller.
     *
     * @param pCaregiverService the caregiver service
     */
    public CaregiverController(final CaregiverService pCaregiverService) {
        this.caregiverService =
                pCaregiverService;
    }

    /**
     * Gets all caregivers.
     *
     * @return the all caregivers
     */
    @GetMapping
    public List<CaregiverDto> getAllCaregivers() {
        return caregiverService.findAll();
    }

    /**
     * Create caregiver caregiver dto.
     *
     * @param caregiverDto the caregiver dto
     * @return the caregiver dto
     */
    @PostMapping
    public CaregiverDto createCaregiver(
            @RequestBody final CaregiverDto caregiverDto) {
        return caregiverService.create(caregiverDto);
    }

    /**
     * Gets caregiver.
     *
     * @param id the id
     * @return the caregiver
     */
    @GetMapping("/{id}")
    public CaregiverDto getCaregiver(
            @PathVariable final Long id) {
        return caregiverService.findById(id);
    }

    /**
     * Update caregiver caregiver dto.
     *
     * @param id           the id
     * @param caregiverDto the caregiver dto
     * @return the caregiver dto
     */
    @PutMapping("/{id}")
    public CaregiverDto updateCaregiver(
            @PathVariable final Long id,
            @RequestBody final CaregiverDto caregiverDto) {
        return caregiverService.update(id, caregiverDto);
    }

    /**
     * Delete caregiver boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/{id}")
    public Boolean deleteCaregiver(
            @PathVariable final Long id) {
        return caregiverService.delete(id);
    }
}
