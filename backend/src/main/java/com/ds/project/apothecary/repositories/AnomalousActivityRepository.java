package com.ds.project.apothecary.repositories;

import com.ds.project.apothecary.entities.AnomalousActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalousActivityRepository
        extends JpaRepository<AnomalousActivity, Long> {

}
