package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.DoctorDto;
import com.ds.project.apothecary.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Doctor controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    /**
     * The Doctor service.
     */
    private final DoctorService
            doctorService;

    /**
     * Instantiates a new Doctor controller.
     *
     * @param pDoctorService the doctor service
     */
    public DoctorController(final DoctorService pDoctorService) {
        this.doctorService =
                pDoctorService;
    }

    /**
     * Gets all doctors.
     *
     * @return the all doctors
     */
    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    /**
     * Create doctor doctor dto.
     *
     * @param doctorDto the doctor dto
     * @return the doctor dto
     */
    @PostMapping
    public DoctorDto createDoctor(
            @RequestBody final DoctorDto doctorDto) {
        return doctorService.create(doctorDto);
    }

    /**
     * Gets doctor.
     *
     * @param id the id
     * @return the doctor
     */
    @GetMapping("/{id}")
    public DoctorDto getDoctor(
            @PathVariable final Long id) {
        return doctorService.findById(id);
    }

    /**
     * Update doctor doctor dto.
     *
     * @param id        the id
     * @param doctorDto the doctor dto
     * @return the doctor dto
     */
    @PutMapping("/{id}")
    public DoctorDto updateDoctor(
            @PathVariable final Long id,
            @RequestBody final DoctorDto doctorDto) {
        return doctorService.update(id, doctorDto);
    }

    /**
     * Delete doctor boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/{id}")
    public Boolean deleteDoctor(
            @PathVariable final Long id) {
        return doctorService.delete(id);
    }
}
