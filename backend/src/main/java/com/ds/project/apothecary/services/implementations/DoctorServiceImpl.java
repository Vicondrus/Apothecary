package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.dtos.DoctorDto;
import com.ds.project.apothecary.entities.Doctor;
import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.repositories.DoctorRepository;
import com.ds.project.apothecary.services.DoctorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The type Doctor service.
 */
@Service
public class DoctorServiceImpl
        implements DoctorService {

    /**
     * The Doctor repository.
     */
    private final DoctorRepository
            doctorRepository;
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
     * Instantiates a new Doctor service.
     *
     * @param pDoctorRepository the doctor repository
     * @param pEncoder          the encoder
     */
    public DoctorServiceImpl(final DoctorRepository pDoctorRepository,
                             final PasswordEncoder pEncoder) {
        this.doctorRepository =
                pDoctorRepository;
        this.encoder =
                pEncoder;
    }

    /**
     * Create doctor dto.
     *
     * @param doctorDto the doctor dto
     * @return the doctor dto
     */
    @Override
    public DoctorDto create(final DoctorDto doctorDto) {
        AddressDto
                addressDto =
                doctorDto.getAddress();

        Doctor
                doctor =
                Doctor.builder()
                        .birthDate(doctorDto.getBirthDate())
                        .username(doctorDto.getUsername())
                        .firstName(doctorDto.getFirstName())
                        .gender(doctorDto.getGender())
                        .lastName(doctorDto.getLastName())
                        .password(encoder.encode(doctorDto.getPassword()))
                        .userType(UserType.DOCTOR)
                        .build();

        Utilities.checkAddress(entityManager, doctor, addressDto);

        return new DoctorDto(doctorRepository.save(doctor));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<DoctorDto> findAll() {
        return doctorRepository.findAll().stream().map(DoctorDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Find by id doctor dto.
     *
     * @param id the id
     * @return the doctor dto
     */
    @Override
    public DoctorDto findById(final Long id) {
        return new DoctorDto(
                doctorRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No "
                                + "Doctor"
                                + " with given id: "
                                + id)));
    }

    /**
     * Update doctor dto.
     *
     * @param id        the id
     * @param doctorDto the doctor dto
     * @return the doctor dto
     */
    @Override
    public DoctorDto update(final Long id,
                            final DoctorDto doctorDto) {
        AddressDto
                addressDto =
                doctorDto.getAddress();

        Doctor
                doctor =
                doctorRepository.getOne(id);

        Utilities.updateUser(doctor, doctorDto, entityManager, encoder,
                addressDto);

        return new DoctorDto(doctorRepository.save(doctor));
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public Boolean delete(final Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
