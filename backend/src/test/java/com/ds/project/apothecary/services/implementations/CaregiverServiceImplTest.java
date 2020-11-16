package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.CaregiverDto;
import com.ds.project.apothecary.entities.Caregiver;
import com.ds.project.apothecary.repositories.CaregiverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Caregiver service impl test.
 */
public class CaregiverServiceImplTest {

    /**
     * The Doctor repository.
     */
    private CaregiverRepository
            doctorRepository;

    /**
     * The Doctor service.
     */
    private CaregiverServiceImpl
            doctorService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        doctorRepository =
                Mockito.mock(CaregiverRepository.class);
        PasswordEncoder
                passwordEncoder =
                Mockito.mock(PasswordEncoder.class);
        doctorService =
                new CaregiverServiceImpl(doctorRepository,
                        passwordEncoder);
    }

    /**
     * Find all test.
     */
    @Test
    public void findAllTest() {
        List<CaregiverDto>
                list1 =
                new ArrayList<CaregiverDto>();
        List<Caregiver>
                list2 =
                new ArrayList<Caregiver>();
        for (int
             i =
             0; i <
                     10; i++) {
            Caregiver
                    p =
                    Caregiver.builder().username(String.valueOf(i)).build();
            list2.add(p);
            list1.add(new CaregiverDto(p));
        }

        Mockito.when(doctorRepository.findAll()).thenReturn(list2);

        List<CaregiverDto>
                list3 =
                doctorService.findAll();

        Assertions.assertIterableEquals(list1, list3);
    }

    /**
     * Find by id test.
     */
    @Test
    void findByIdTest() {
        Caregiver
                u =
                Caregiver.builder().username("user").id((long) 1).build();

        Mockito.when(doctorRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(u));

        CaregiverDto
                check =
                new CaregiverDto(u);

        CaregiverDto
                x =
                doctorService.findById((long) 1);
        Assertions.assertEquals(check, x);
    }

    /**
     * Create test.
     */
    @Test
    void createTest() {
        Caregiver
                u =
                Caregiver.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Caregiver)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .save(Mockito.any(Caregiver.class));

        CaregiverDto
                check =
                new CaregiverDto(u);

        CaregiverDto
                x =
                doctorService.create(check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Update test.
     */
    @Test
    void updateTest() {
        Caregiver
                u =
                Caregiver.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Caregiver)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .save(Mockito.any(Caregiver.class));

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Long)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .getOne((long) 1);

        CaregiverDto
                check =
                new CaregiverDto(u);

        CaregiverDto
                x =
                doctorService.update((long) 1, check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Delete test.
     */
    @Test
    void deleteTest() {
        Caregiver
                u =
                Caregiver.builder().username("user").id((long) 1).build();

        Mockito.when(doctorRepository.existsById((long) 1))
                .thenReturn(Boolean.TRUE);

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Caregiver)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .delete(Mockito.any(Caregiver.class));

        Boolean
                x =
                doctorService.delete((long) 1);
        Assertions.assertTrue(x);
    }
}