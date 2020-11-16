package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.AddressDto;
import com.ds.project.apothecary.services.AddressService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Address controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    /**
     * The Address service.
     */
    private final AddressService
            addressService;

    /**
     * Instantiates a new Address controller.
     *
     * @param pAddressService the address service
     */
    public AddressController(
            final AddressService pAddressService) {
        this.addressService =
                pAddressService;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public List<AddressDto> getAll() {
        return addressService.findAll();
    }
}
