package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.DoctorDto;
import com.ds.project.apothecary.entities.Doctor;
import com.ds.project.apothecary.repositories.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor service impl test.
 */
public class DoctorServiceImplTest {

    /**
     * The Doctor repository.
     */
    private DoctorRepository
            doctorRepository;

    /**
     * The Doctor service.
     */
    private DoctorServiceImpl
            doctorService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        doctorRepository =
                Mockito.mock(DoctorRepository.class);
        PasswordEncoder
                passwordEncoder =
                Mockito.mock(PasswordEncoder.class);
        doctorService =
                new DoctorServiceImpl(doctorRepository,
                        passwordEncoder);
    }

    /**
     * Find all test.
     */
    @Test
    public void findAllTest() {
        List<DoctorDto>
                list1 =
                new ArrayList<DoctorDto>();
        List<Doctor>
                list2 =
                new ArrayList<Doctor>();
        for (int
             i =
             0; i <
                     10; i++) {
            Doctor
                    p =
                    Doctor.builder().username(String.valueOf(i)).build();
            list2.add(p);
            list1.add(new DoctorDto(p));
        }

        Mockito.when(doctorRepository.findAll()).thenReturn(list2);

        List<DoctorDto>
                list3 =
                doctorService.findAll();

        Assertions.assertIterableEquals(list1, list3);
    }

    /**
     * Find by id test.
     */
    @Test
    void findByIdTest() {
        Doctor
                u =
                Doctor.builder().username("user").id((long) 1).build();

        Mockito.when(doctorRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(u));

        DoctorDto
                check =
                new DoctorDto(u);

        DoctorDto
                x =
                doctorService.findById((long) 1);
        Assertions.assertEquals(check, x);
    }

    /**
     * Create test.
     */
    @Test
    void createTest() {
        Doctor
                u =
                Doctor.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Doctor)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .save(Mockito.any(Doctor.class));

        DoctorDto
                check =
                new DoctorDto(u);

        DoctorDto
                x =
                doctorService.create(check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Update test.
     */
    @Test
    void updateTest() {
        Doctor
                u =
                Doctor.builder().username("user").id((long) 1).build();

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Doctor)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .save(Mockito.any(Doctor.class));

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Long)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .getOne((long) 1);

        DoctorDto
                check =
                new DoctorDto(u);

        DoctorDto
                x =
                doctorService.update((long) 1, check);
        Assertions.assertEquals(check, x);
    }

    /**
     * Delete test.
     */
    @Test
    void deleteTest() {
        Doctor
                u =
                Doctor.builder().username("user").id((long) 1).build();

        Mockito.when(doctorRepository.existsById((long) 1))
                .thenReturn(Boolean.TRUE);

        Mockito.doAnswer(
                invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof Doctor)
                        return u;
                    return null;
                })
                .when(doctorRepository)
                .delete(Mockito.any(Doctor.class));

        Boolean
                x =
                doctorService.delete((long) 1);
        Assertions.assertTrue(x);
    }
}