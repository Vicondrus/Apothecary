package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.dtos.CaregiverDto;
import com.ds.project.apothecary.dtos.PatientDto;
import com.ds.project.apothecary.entities.Caregiver;
import com.ds.project.apothecary.entities.MedicalRecord;
import com.ds.project.apothecary.entities.Patient;
import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.repositories.PatientRepository;
import com.ds.project.apothecary.services.PatientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The type Patient service.
 */
@Service
public class PatientServiceImpl
        implements PatientService {

    /**
     * The Patient repository.
     */
    private final PatientRepository
            patientRepository;
    /**
     * The Encoder.
     */
    private final PasswordEncoder
            encoder;
    /**
     * The Entity manager.
     */
    @PersistenceContext
    private EntityManager
            entityManager;

    /**
     * Instantiates a new Patient service.
     *
     * @param pPatientRepository the patient repository
     * @param pEncoder           the encoder
     */
    public PatientServiceImpl(
            final PatientRepository pPatientRepository,
            final PasswordEncoder pEncoder) {
        this.patientRepository =
                pPatientRepository;
        this.encoder =
                pEncoder;
    }

    /**
     * Create patient dto.
     *
     * @param patientDto the patient dto
     * @return the patient dto
     */
    @Override
    public PatientDto create(final PatientDto patientDto) {
        AddressDto
                addressDto =
                patientDto.getAddress();

        Patient
                patient =
                Patient.builder()
                        .birthDate(patientDto.getBirthDate())
                        .username(patientDto.getUsername())
                        .firstName(patientDto.getFirstName())
                        .gender(patientDto.getGender())
                        .lastName(patientDto.getLastName())
                        .password(encoder.encode(patientDto.getPassword()))
                        .userType(UserType.PATIENT)
                        .medicationPlans(new HashSet<>())
                        .build();

        MedicalRecord
                mr =
                MedicalRecord.builder().description("").patient(patient)
                        .build();
        patient.setMedicalRecord(mr);

        Utilities.checkAddress(entityManager, patient, addressDto);
        checkCaregiver(patient, patientDto.getCaregiver());

        return new PatientDto(patientRepository.save(patient));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<PatientDto> findAll() {
        return patientRepository.findAll().stream().map(PatientDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Find by id patient dto.
     *
     * @param id the id
     * @return the patient dto
     */
    @Override
    public PatientDto findById(final Long id) {
        return new PatientDto(
                patientRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No "
                                + "Patient with given"
                                + " id: "
                                + id)));
    }

    /**
     * Update patient dto.
     *
     * @param id         the id
     * @param patientDto the patient dto
     * @return the patient dto
     */
    @Override
    public PatientDto update(final Long id,
                             final PatientDto patientDto) {
        AddressDto
                addressDto =
                patientDto.getAddress();

        Patient
                patient =
                patientRepository.getOne(id);

        Utilities.updateUser(patient, patientDto, entityManager, encoder,
                addressDto);
        checkCaregiver(patient, patientDto.getCaregiver());

        return new PatientDto(patientRepository.save(patient));
    }

    /**
     * Check caregiver.
     *
     * @param patient      the patient
     * @param caregiverDto the caregiver dto
     */
    private void checkCaregiver(final Patient patient,
                                final CaregiverDto caregiverDto) {
        if (caregiverDto != null) {
            patient.setCaregiver(entityManager.getReference(Caregiver.class,
                    caregiverDto.getId()));
        }
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public Boolean delete(final Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Find all by caregiver id list.
     *
     * @param id the id
     * @return the list
     */
    @Override
    public List<PatientDto> findAllByCaregiverId(final Long id) {
        return patientRepository.findAllByCaregiverId(id).stream()
                .map(PatientDto::new)
                .collect(Collectors.toList());
    }
}
