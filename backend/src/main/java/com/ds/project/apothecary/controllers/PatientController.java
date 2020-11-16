package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.PatientDto;
import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.security.UserDetailsImpl;
import com.ds.project.apothecary.services.PatientService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Patient controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/patients")
public class PatientController {

    /**
     * The Patient service.
     */
    private final PatientService
            patientService;

    /**
     * Instantiates a new Patient controller.
     *
     * @param pPatientService the patient service
     */
    public PatientController(final PatientService pPatientService) {
        this.patientService =
                pPatientService;
    }

    /**
     * Gets all patients.
     *
     * @param authentication the authentication
     * @return the all patients
     */
    @GetMapping
    public List<PatientDto> getAllPatients(
            final Authentication authentication) {
        if (authentication
                .getAuthorities()
                .contains(
                        new SimpleGrantedAuthority(
                                UserType.DOCTOR.toString()))) {
            return patientService.findAll();
        }
        if (authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(
                        UserType.CAREGIVER.toString()))) {
            return patientService.findAllByCaregiverId(
                    ((UserDetailsImpl) authentication.getPrincipal()).getId());
        }
        return new ArrayList<>();
    }

    /**
     * Create patient patient dto.
     *
     * @param patientDto the patient dto
     * @return the patient dto
     */
    @PostMapping
    public PatientDto createPatient(
            @RequestBody final PatientDto patientDto) {
        return patientService.create(patientDto);
    }

    /**
     * Gets patient.
     *
     * @param id the id
     * @return the patient
     */
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @PathVariable final Long id) {
        return patientService.findById(id);
    }

    /**
     * Update patient patient dto.
     *
     * @param id         the id
     * @param patientDto the patient dto
     * @return the patient dto
     */
    @PutMapping("/{id}")
    public PatientDto updatePatient(
            @PathVariable final Long id,
            @RequestBody final PatientDto patientDto) {
        return patientService.update(id, patientDto);
    }

    /**
     * Delete patient boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/{id}")
    public Boolean deletePatient(
            @PathVariable final Long id) {
        return patientService.delete(id);
    }
}
