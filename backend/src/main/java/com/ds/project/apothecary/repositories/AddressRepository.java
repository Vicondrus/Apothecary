package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Address repository.
 */
@Repository
public interface AddressRepository
        extends JpaRepository<Address, Long> {
}
