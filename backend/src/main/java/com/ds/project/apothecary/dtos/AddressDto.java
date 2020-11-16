package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Address dto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {

    /**
     * The Id.
     */
    private Long
            id;

    /**
     * The Street.
     */
    private String
            street;

    /**
     * The Number.
     */
    private Integer
            number;

    /**
     * The City.
     */
    private String
            city;

    /**
     * The Country.
     */
    private String
            country;

    /**
     * Instantiates a new Address dto.
     *
     * @param address the address
     */
    public AddressDto(final Address address) {
        if (address == null) {
            return;
        }
        this.id =
                address.getId();
        this.street =
                address.getStreet();
        this.city =
                address.getCity();
        this.number =
                address.getNumber();
        this.country =
                address.getCountry();
    }
}
