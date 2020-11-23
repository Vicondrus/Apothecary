package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.MedicationPlanDetailsDto;
import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.enums.MedicationStatus;

import java.util.Date;
import java.util.List;

public interface MedicationDetailsService {

    List<MedicationPlanDetailsDto> getMedicationsForGivenHour(Integer hour);

    MedicationPlanDto setMedicationPlanDtoForDate(Date date);

    void setMedicationStatus(Date date,
                             MedicationPlanDetailsDto medicationPlanDetailsDto,
                             MedicationStatus medicationStatus);

    void setPreviousMedicationsAsNotTaken(Date date, Integer hour);
}
