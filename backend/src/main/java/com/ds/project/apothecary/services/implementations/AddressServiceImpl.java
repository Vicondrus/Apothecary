package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.repositories.AddressRepository;
import com.ds.project.apothecary.services.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Address service.
 */
@Service
public class AddressServiceImpl
        implements AddressService {

    /**
     * The Address repository.
     */
    private final AddressRepository
            addressRepository;

    /**
     * Instantiates a new Address service.
     *
     * @param pAddressRepository the address repository
     */
    public AddressServiceImpl(final AddressRepository pAddressRepository) {
        this.addressRepository =
                pAddressRepository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream().map(AddressDto::new)
                .collect(Collectors.toList());
    }
}
