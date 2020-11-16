package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.entities.Address;
import com.ds.project.apothecary.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Address service impl test.
 */
public class AddressServiceImplTest {

    /**
     * The Address repository.
     */
    private AddressRepository
            addressRepository;

    /**
     * The Address service.
     */
    private AddressServiceImpl
            addressService;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        addressRepository =
                Mockito.mock(AddressRepository.class);
        addressService =
                new AddressServiceImpl(addressRepository);
    }

    /**
     * Find all test.
     */
    @Test
    public void findAllTest() {
        List<AddressDto>
                list1 =
                new ArrayList<AddressDto>();
        List<Address>
                list2 =
                new ArrayList<Address>();
        for (int
             i =
             0; i <
                     10; i++) {
            Address
                    a =
                    Address.builder()
                            .id((long) i)
                            .street(String.valueOf(i))
                            .country(String.valueOf(i))
                            .city(String.valueOf(i))
                            .number(i)
                            .build();
            list2.add(a);
            list1.add(new AddressDto(a));
        }

        Mockito.when(addressRepository.findAll()).thenReturn(list2);

        List<AddressDto>
                list3 =
                addressService.findAll();

        Assertions.assertIterableEquals(list1, list3);
    }
}
