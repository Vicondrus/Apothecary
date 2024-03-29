package com.ds.project.apothecary.services;

import com.ds.project.apothecary.dtos.DailyMedicationStatusDto;
import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.enums.MedicationStatus;

import java.util.Date;

/**
 * The interface Pill box service.
 */
public interface PillBoxService {

    /**
     * Test service string.
     *
     * @param testString the test string
     * @return the string
     */
    String testService(String testString);

    MedicationPlanDto retrieveMedicationPlanForToday(Date today,
                                                     Long patientId);

    DailyMedicationStatusDto setStatusForMedicationDetailToday(Date today,
                                                               Long medicationDetailId,
                                                               MedicationStatus medicationStatus);

}
