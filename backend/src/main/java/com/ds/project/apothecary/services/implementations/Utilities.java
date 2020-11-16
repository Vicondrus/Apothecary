package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.dtos.UserDto;
import com.ds.project.apothecary.entities.Address;
import com.ds.project.apothecary.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Utilities.
 */
public final class Utilities {

    /**
     * Instantiates a new Utilities.
     */
    private Utilities() {
    }

    /**
     * Check address.
     *
     * @param entityManager the entity manager
     * @param user          the user
     * @param addressDto    the address dto
     */
    static void checkAddress(
            final EntityManager entityManager,
            final User user,
            final AddressDto addressDto) {
        if (addressDto == null) {
            return;
        }
        if (addressDto.getId() == null) {
            Set<User>
                    auxSet =
                    new HashSet<>();
            auxSet.add(user);
            Address
                    address =
                    Address.builder()
                            .city(addressDto.getCity())
                            .country(addressDto.getCountry())
                            .number(addressDto.getNumber())
                            .street(addressDto.getStreet())
                            .inhabitants(auxSet)
                            .build();
            user.setAddress(address);
        } else {
            user.setAddress(entityManager.getReference(Address.class,
                    addressDto.getId()));
        }
    }

    /**
     * Update user.
     *
     * @param user          the user
     * @param userDto       the user dto
     * @param entityManager the entity manager
     * @param encoder       the encoder
     * @param addressDto    the address dto
     */
    static void updateUser(
            final User user,
            final UserDto userDto,
            final EntityManager entityManager,
            final PasswordEncoder encoder,
            final AddressDto addressDto) {
        user.setBirthDate(userDto.getBirthDate());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());

        if (userDto.getPassword() != null) {
            user.setPassword(encoder.encode(userDto.getPassword()));
        }

        Utilities.checkAddress(entityManager, user, addressDto);
    }
}
