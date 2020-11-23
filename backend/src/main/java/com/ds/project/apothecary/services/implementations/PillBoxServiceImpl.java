package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.DailyMedicationStatusDto;
import com.ds.project.apothecary.dtos.MedicationPlanDetailsDto;
import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.entities.DailyMedicationStatus;
import com.ds.project.apothecary.entities.MedicationPlan;
import com.ds.project.apothecary.entities.MedicationPlanDetails;
import com.ds.project.apothecary.enums.MedicationStatus;
import com.ds.project.apothecary.repositories.DailyMedicationStatusRepository;
import com.ds.project.apothecary.repositories.MedicationPlanDetailsRepository;
import com.ds.project.apothecary.repositories.MedicationPlanRepository;
import com.ds.project.apothecary.services.PillBoxService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * The type Pill box service.
 */
@Service
public class PillBoxServiceImpl implements PillBoxService {

    private final MedicationPlanRepository medicationPlanRepository;

    private final MedicationPlanDetailsRepository
            medicationPlanDetailsRepository;

    private final DailyMedicationStatusRepository
            dailyMedicationStatusRepository;

    public PillBoxServiceImpl(
            MedicationPlanRepository medicationPlanRepository,
            MedicationPlanDetailsRepository medicationPlanDetailsRepository,
            DailyMedicationStatusRepository dailyMedicationStatusRepository) {
        this.medicationPlanRepository = medicationPlanRepository;
        this.medicationPlanDetailsRepository = medicationPlanDetailsRepository;
        this.dailyMedicationStatusRepository = dailyMedicationStatusRepository;
    }

    @Override public String testService(String testString) {
        System.out.println("Working: " + testString);
        return "LOL. It works!";
    }

    @Override
    public MedicationPlanDto retrieveMedicationPlanForToday(Date today,
                                                            Long patientId) {
        List<MedicationPlan> medicationPlanList =
                medicationPlanRepository.
                        getAllByPatientIdAndPeriodEndAfterAndPeriodStartBefore(
                                patientId, today, today);
        if (medicationPlanList.isEmpty()){
            return null;
        }
        MedicationPlanDto medicationPlanDto =
                new MedicationPlanDto(medicationPlanList.get(0));
        if (medicationPlanList.size() > 1) {
            for (int i = 1; i < medicationPlanList.size(); i++) {
                for (MedicationPlanDetails medicationPlanDetails :
                        medicationPlanList.get(i)
                                .getMedicationPlanDetailsSet()) {
                    medicationPlanDto.addMedicationPlanDetailsToList(
                            new MedicationPlanDetailsDto(
                                    medicationPlanDetails));
                }
            }
        }

        return medicationPlanDto;
    }

    @Override public DailyMedicationStatusDto setStatusForMedicationDetailToday(
            Date today, Long medicationDetailId,
            MedicationStatus medicationStatus) {
        return new DailyMedicationStatusDto(dailyMedicationStatusRepository
                .save(DailyMedicationStatus.builder().date(today)
                        .status(medicationStatus).medicationPlanDetails(
                                medicationPlanDetailsRepository
                                        .getOne(medicationDetailId)).build()));
    }
}
