package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.security.UserDetailsImpl;
import com.ds.project.apothecary.services.MedicationPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Patient med plan controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/medication-plans")
public class PatientMedPlanController {

    /**
     * The Medication plan service.
     */
    private final MedicationPlanService
            medicationPlanService;

    /**
     * Instantiates a new Patient med plan controller.
     *
     * @param pMedicationPlanService the medication plan service
     */
    public PatientMedPlanController(
            final MedicationPlanService pMedicationPlanService) {
        this.medicationPlanService =
                pMedicationPlanService;
    }

    /**
     * Gets all medication plans.
     *
     * @param authentication the authentication
     * @return the all medication plans
     */
    @GetMapping
    public List<MedicationPlanDto> getAllMedicationPlans(
            final Authentication authentication) {
        return medicationPlanService.findAllForPatientId(
                ((UserDetailsImpl) authentication.getPrincipal()).getId());
    }
}
