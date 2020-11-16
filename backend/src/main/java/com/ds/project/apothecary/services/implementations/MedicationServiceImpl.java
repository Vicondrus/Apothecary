package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.MedicationDto;
import com.ds.project.apothecary.entities.Medication;
import com.ds.project.apothecary.repositories.MedicationRepository;
import com.ds.project.apothecary.services.MedicationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The type Medication service.
 */
@Service
public class MedicationServiceImpl
        implements MedicationService {

    /**
     * The Medication repository.
     */
    private final MedicationRepository
            medicationRepository;

    /**
     * Instantiates a new Medication service.
     *
     * @param pMedicationRepository the medication repository
     */
    public MedicationServiceImpl(
            final MedicationRepository pMedicationRepository) {
        this.medicationRepository =
                pMedicationRepository;
    }

    /**
     * Create medication dto.
     *
     * @param medicationDto the medication dto
     * @return the medication dto
     */
    @Override
    public MedicationDto create(final MedicationDto medicationDto) {
        Medication
                medication =
                Medication.builder()
                        .dosage(medicationDto.getDosage())
                        .dosageUnits(medicationDto.getDosageUnits())
                        .medicationPlanDetailsSet(new HashSet<>())
                        .name(medicationDto.getName())
                        .sideEffects(medicationDto.getSideEffects())
                        .build();
        return new MedicationDto(medicationRepository.save(medication));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<MedicationDto> findAll() {
        return medicationRepository.findAll().stream()
                .map(MedicationDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Find by id medication dto.
     *
     * @param id the id
     * @return the medication dto
     */
    @Override
    public MedicationDto findById(final Long id) {
        return new MedicationDto(
                medicationRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new NoSuchElementException("No "
                                        + "Medication"
                                        + " with given id: "
                                        + id)));
    }

    /**
     * Update medication dto.
     *
     * @param id            the id
     * @param medicationDto the medication dto
     * @return the medication dto
     */
    @Override
    public MedicationDto update(final Long id,
                                final MedicationDto medicationDto) {
        Medication
                medication =
                medicationRepository.getOne(id);

        medication.setDosage(medicationDto.getDosage());
        medication.setDosageUnits(medicationDto.getDosageUnits());
        medication.setName(medicationDto.getName());
        medication.setSideEffects(medicationDto.getSideEffects());

        return new MedicationDto(medicationRepository.save(medication));
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public Boolean delete(final Long id) {
        if (medicationRepository.existsById(id)) {
            medicationRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Find all by name like list.
     *
     * @param name the name
     * @return the list
     */
    @Override
    public List<MedicationDto> findAllByNameLike(final String name) {
        return medicationRepository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .map(MedicationDto::new)
                .collect(Collectors.toList());
    }
}
