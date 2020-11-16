package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.MedicationDto;
import com.ds.project.apothecary.entities.Medication;
import com.ds.project.apothecary.repositories.MedicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Medication service impl test.
 */
public class MedicationServiceImplTest {

    /**
     * The Medication repository.
     */
    private MedicationRepository
            medicationRepository;

    /**
     * The Medication service.
     */
    private MedicationServiceImpl
            medicationService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        medicationRepository =
                Mockito.mock(MedicationRepository.class);
        medicationService =
                new MedicationServiceImpl(medicationRepository);
    }

    /**
     * Find all test.
     */
    @Test
    public void findAllTest() {
        List<MedicationDto>
                list1 =
                new ArrayList<MedicationDto>();
        List<Medication>
                list2 =
                new ArrayList<Medication>();
        for (int
             i =
             0; i <
                     10; i++) {
            Medication
                    p =
                    Medication.builder().name(String.valueOf(i)).build();
            list2.add(p);
            list1.add(new MedicationDto(p));
        }

        Mockito.when(medicationRepository.findAll()).thenReturn(list2);

        List<MedicationDto>
                list3 =
                medicationService.findAll();

        Assertions.assertIterableEquals(list1, list3);
    }

    /**
     * Find by id test.
     */
    @Test
    void findByIdTest() {
        Medication
                m =
                Medication.builder().name("name").id((long) 1).build();

        Mockito.when(medicationRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(m));

        MedicationDto
                check =
                new MedicationDto(m);

        MedicationDto
                x =
                medicationService.findById((long) 1);
        Assertions.assertEquals(check, x);
    }

    /**
     * Create test.
     */
    @Test
    void createTest() {
        Medication
                m =
                Medication.builder().name("name").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock
                            .getArguments()[0] instanceof Medication)
                        return m;
                    return null;
                })
                .when(medicationRepository)
                .save(Mockito.any(Medication.class));

        MedicationDto
                check =
                new MedicationDto(m);

        MedicationDto
                x =
                medicationService.create(check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Update test.
     */
    @Test
    void updateTest() {
        Medication
                m =
                Medication.builder().name("name").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock
                            .getArguments()[0] instanceof Medication)
                        return m;
                    return null;
                })
                .when(medicationRepository)
                .save(Mockito.any(Medication.class));

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Long)
                        return m;
                    return null;
                })
                .when(medicationRepository)
                .getOne((long) 1);

        MedicationDto
                check =
                new MedicationDto(m);

        MedicationDto
                x =
                medicationService.update((long) 1, check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Delete test.
     */
    @Test
    void deleteTest() {
        Medication
                m =
                Medication.builder().name("name").id((long) 1).build();

        Mockito.when(medicationRepository.existsById((long) 1))
                .thenReturn(Boolean.TRUE);

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock
                            .getArguments()[0] instanceof Medication)
                        return m;
                    return null;
                })
                .when(medicationRepository)
                .delete(Mockito.any(Medication.class));

        Boolean
                x =
                medicationService.delete((long) 1);
        Assertions.assertTrue(x);
    }
}