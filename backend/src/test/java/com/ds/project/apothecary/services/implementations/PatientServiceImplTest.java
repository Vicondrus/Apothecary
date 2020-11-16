package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.PatientDto;
import com.ds.project.apothecary.entities.Patient;
import com.ds.project.apothecary.repositories.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Patient service impl test.
 */
public class PatientServiceImplTest {

    /**
     * The Patient repository.
     */
    private PatientRepository
            patientRepository;

    /**
     * The Patient service.
     */
    private PatientServiceImpl
            patientService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        patientRepository =
                Mockito.mock(PatientRepository.class);
        PasswordEncoder
                passwordEncoder =
                Mockito.mock(PasswordEncoder.class);
        patientService =
                new PatientServiceImpl(patientRepository, passwordEncoder);
    }

    /**
     * Find all test.
     */
    @Test
    public void findAllTest() {
        List<PatientDto>
                list1 =
                new ArrayList<PatientDto>();
        List<Patient>
                list2 =
                new ArrayList<Patient>();
        for (int
             i =
             0; i <
                     10; i++) {
            Patient
                    p =
                    Patient.builder().username(String.valueOf(i)).build();
            list2.add(p);
            list1.add(new PatientDto(p));
        }

        Mockito.when(patientRepository.findAll()).thenReturn(list2);

        List<PatientDto>
                list3 =
                patientService.findAll();

        Assertions.assertIterableEquals(list1, list3);
    }

    /**
     * Find by id test.
     */
    @Test
    void findByIdTest() {
        Patient
                u =
                Patient.builder().username("user").id((long) 1).build();

        Mockito.when(patientRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(u));

        PatientDto
                check =
                new PatientDto(u);

        PatientDto
                x =
                patientService.findById((long) 1);
        Assertions.assertEquals(check, x);
    }

    /**
     * Create test.
     */
    @Test
    void createTest() {
        Patient
                u =
                Patient.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Patient)
                        return u;
                    return null;
                })
                .when(patientRepository)
                .save(Mockito.any(Patient.class));

        PatientDto
                check =
                new PatientDto(u);

        PatientDto
                x =
                patientService.create(check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Update test.
     */
    @Test
    void updateTest() {
        Patient
                u =
                Patient.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Patient)
                        return u;
                    return null;
                })
                .when(patientRepository)
                .save(Mockito.any(Patient.class));

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Long)
                        return u;
                    return null;
                })
                .when(patientRepository)
                .getOne((long) 1);

        PatientDto
                check =
                new PatientDto(u);

        PatientDto
                x =
                patientService.update((long) 1, check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Delete test.
     */
    @Test
    void deleteTest() {
        Patient
                u =
                Patient.builder().username("user").id((long) 1).build();

        Mockito.when(patientRepository.existsById((long) 1))
                .thenReturn(Boolean.TRUE);

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Patient)
                        return u;
                    return null;
                })
                .when(patientRepository)
                .delete(Mockito.any(Patient.class));

        Boolean
                x =
                patientService.delete((long) 1);
        Assertions.assertTrue(x);
    }

    /**
     * Find all by caregiver id test.
     */
    @Test
    void findAllByCaregiverIdTest() {
        List<PatientDto>
                list1 =
                new ArrayList<PatientDto>();
        List<Patient>
                list2 =
                new ArrayList<Patient>();
        for (int
             i =
             0; i <
                     10; i++) {
            Patient
                    p =
                    Patient.builder().username(String.valueOf(i)).build();
            list2.add(p);
            list1.add(new PatientDto(p));
        }

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Long)
                        return list2;
                    return null;
                })
                .when(patientRepository)
                .findAllByCaregiverId((long) 20);

        List<PatientDto>
                list3 =
                patientService.findAllByCaregiverId((long) 20);

        Assertions.assertIterableEquals(list1, list3);
    }
}
