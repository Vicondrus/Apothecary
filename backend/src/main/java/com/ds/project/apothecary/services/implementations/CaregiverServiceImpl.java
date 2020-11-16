package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.dtos.CaregiverDto;
import com.ds.project.apothecary.entities.Caregiver;
import com.ds.project.apothecary.enums.UserType;
import com.ds.project.apothecary.repositories.CaregiverRepository;
import com.ds.project.apothecary.services.CaregiverService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The type Caregiver service.
 */
@Service
public class CaregiverServiceImpl
        implements CaregiverService {

    /**
     * The Caregiver repository.
     */
    private final CaregiverRepository
            caregiverRepository;
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
     * Instantiates a new Caregiver service.
     *
     * @param pCaregiverRepository the caregiver repository
     * @param pEncoder             the encoder
     */
    public CaregiverServiceImpl(
            final CaregiverRepository pCaregiverRepository,
            final PasswordEncoder pEncoder) {
        this.caregiverRepository =
                pCaregiverRepository;
        this.encoder =
                pEncoder;
    }

    /**
     * Create caregiver dto.
     *
     * @param caregiverDto the caregiver dto
     * @return the caregiver dto
     */
    @Override
    public CaregiverDto create(final CaregiverDto caregiverDto) {
        AddressDto
                addressDto =
                caregiverDto.getAddress();

        Caregiver
                caregiver =
                Caregiver.builder()
                        .birthDate(caregiverDto.getBirthDate())
                        .username(caregiverDto.getUsername())
                        .firstName(caregiverDto.getFirstName())
                        .gender(caregiverDto.getGender())
                        .lastName(caregiverDto.getLastName())
                        .password(encoder.encode(caregiverDto.getPassword()))
                        .userType(UserType.CAREGIVER)
                        .build();

        Utilities.checkAddress(entityManager, caregiver, addressDto);

        return new CaregiverDto(caregiverRepository.save(caregiver));
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<CaregiverDto> findAll() {
        return caregiverRepository.findAll().stream()
                .map(CaregiverDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Find by id caregiver dto.
     *
     * @param id the id
     * @return the caregiver dto
     */
    @Override
    public CaregiverDto findById(final Long id) {
        return new CaregiverDto(
                caregiverRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new NoSuchElementException("No "
                                        + "Caregiver"
                                        + " with given id: "
                                        + id)));
    }

    /**
     * Update caregiver dto.
     *
     * @param id           the id
     * @param caregiverDto the caregiver dto
     * @return the caregiver dto
     */
    @Override
    public CaregiverDto update(final Long id,
                               final CaregiverDto caregiverDto) {
        AddressDto
                addressDto =
                caregiverDto.getAddress();

        Caregiver
                caregiver =
                caregiverRepository.getOne(id);

        Utilities.updateUser(caregiver, caregiverDto, entityManager, encoder,
                addressDto);

        return new CaregiverDto(caregiverRepository.save(caregiver));
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Override
    public Boolean delete(final Long id) {
        if (caregiverRepository.existsById(id)) {
            caregiverRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
