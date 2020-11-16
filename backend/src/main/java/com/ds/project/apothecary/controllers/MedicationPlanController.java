package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.services.MedicationPlanService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Medication plan controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/patients/{patientId}/medication-plans")
public class MedicationPlanController {

    /**
     * The Medication plan service.
     */
    private final MedicationPlanService
            medicationPlanService;

    /**
     * Instantiates a new Medication plan controller.
     *
     * @param pMedicationPlanService the medication plan service
     */
    public MedicationPlanController(
            final MedicationPlanService pMedicationPlanService) {
        this.medicationPlanService =
                pMedicationPlanService;
    }

    /**
     * Gets all medication plans.
     *
     * @param patientId the patient id
     * @return the all medication plans
     */
    @GetMapping
    public List<MedicationPlanDto> getAllMedicationPlans(
            @PathVariable final Long patientId) {
        return medicationPlanService.findAllForPatientId(patientId);
    }

    /**
     * Create medication plan medication plan dto.
     *
     * @param patientId         the patient id
     * @param medicationPlanDto the medication plan dto
     * @return the medication plan dto
     */
    @PostMapping
    public MedicationPlanDto createMedicationPlan(
            @PathVariable final Long patientId,
            @RequestBody final MedicationPlanDto medicationPlanDto) {
        return medicationPlanService.create(patientId, medicationPlanDto);
    }
}
