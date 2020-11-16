package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.AddressDto;

import java.util.List;

/**
 * The interface Address service.
 */
public interface AddressService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<AddressDto> findAll();
}
