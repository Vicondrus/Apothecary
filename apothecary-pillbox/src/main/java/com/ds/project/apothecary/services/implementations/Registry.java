package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.MedicationPlanDetailsDto;
import com.ds.project.apothecary.dtos.MedicationPlanDto;

import java.util.Date;

public class Registry {

    private static final Long PATIENT_ID = 35L;
    private static final Date DOWNLOAD_AT = new Date();
    private static MedicationPlanDto medicationPlanDto =
            new MedicationPlanDto();

    public static Long getPatientId() {
        return PATIENT_ID;
    }

    public static Date getDownloadAt() {
        return DOWNLOAD_AT;
    }

    public static MedicationPlanDto getMedicationPlanDto() {
        return medicationPlanDto;
    }

    public static void setMedicationPlanDto(
            MedicationPlanDto medicationPlanDto) {
        Registry.medicationPlanDto = medicationPlanDto;
    }

    public static void removeFromMedicationPlan(
            MedicationPlanDetailsDto medicationPlanDetailsDto) {
        medicationPlanDto.getMedications().remove(medicationPlanDetailsDto);
    }
}
