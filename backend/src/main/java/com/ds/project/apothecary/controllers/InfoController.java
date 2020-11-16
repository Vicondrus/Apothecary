package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.PatientDto;
import com.ds.project.apothecary.security.UserDetailsImpl;
import com.ds.project.apothecary.services.PatientService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Info controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/information")
public class InfoController {

    /**
     * The Patient service.
     */
    private final PatientService
            patientService;

    /**
     * Instantiates a new Info controller.
     *
     * @param pPatientService the patient service
     */
    public InfoController(final PatientService pPatientService) {
        this.patientService =
                pPatientService;
    }

    /**
     * Gets patient details.
     *
     * @param authentication the authentication
     * @return the patient details
     */
    @GetMapping
    public PatientDto getPatientDetails(final Authentication authentication) {
        return patientService.findById(
                ((UserDetailsImpl) authentication.getPrincipal()).getId());
    }
}
