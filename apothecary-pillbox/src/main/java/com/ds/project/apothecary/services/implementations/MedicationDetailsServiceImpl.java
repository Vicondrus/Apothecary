package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.MedicationPlanDetailsDto;
import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.enums.MedicationStatus;
import com.ds.project.apothecary.services.MedicationDetailsService;
import com.ds.project.apothecary.services.PillBoxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationDetailsServiceImpl implements MedicationDetailsService {

    private final PillBoxService pillBoxService;

    public MedicationDetailsServiceImpl(
            PillBoxService pillBoxService) {
        this.pillBoxService = pillBoxService;
    }

    @Override
    public List<MedicationPlanDetailsDto> getMedicationsForGivenHour(
            Integer hour) {
        MedicationPlanDto medicationPlanDto = Registry.getMedicationPlanDto();
        if (medicationPlanDto.getMedications() == null) {
            return new ArrayList<>();
        }
        return medicationPlanDto.getMedications().parallelStream()
                .filter(medicationPlanDetailsDto ->
                        medicationPlanDetailsDto.getIntakeInterval() <= hour &&
                                hour < medicationPlanDetailsDto
                                        .getIntakeIntervalEnd()).collect(
                        Collectors.toList());
    }

    @Override
    public void setPreviousMedicationsAsNotTaken(Date date, Integer hour) {
        MedicationPlanDto medicationPlanDto = Registry.getMedicationPlanDto();
        List<MedicationPlanDetailsDto> toRemove = new ArrayList<>();
        if (medicationPlanDto.getMedications() != null) {
            medicationPlanDto.getMedications()
                    .forEach(medicationPlanDetailsDto -> {
                        if (medicationPlanDetailsDto.getIntakeIntervalEnd() <=
                                hour) {
                            toRemove.add(medicationPlanDetailsDto);
                            pillBoxService
                                    .setStatusForMedicationDetailToday(date,
                                            medicationPlanDetailsDto.getId(),
                                            MedicationStatus.NOT_TAKEN);
                        }
                    });
        }
        toRemove.forEach(Registry::removeFromMedicationPlan);
    }

    @Override public MedicationPlanDto setMedicationPlanDtoForDate(Date date) {
        MedicationPlanDto medicationPlanDto =
                pillBoxService.retrieveMedicationPlanForToday(date,
                        Registry.getPatientId());
        Registry.setMedicationPlanDto(medicationPlanDto);
        return medicationPlanDto;
    }

    @Override public void setMedicationStatus(Date date,
                                              MedicationPlanDetailsDto medicationPlanDetailsDto,
                                              MedicationStatus medicationStatus) {
        Registry.removeFromMedicationPlan(medicationPlanDetailsDto);
        pillBoxService.setStatusForMedicationDetailToday(date,
                medicationPlanDetailsDto.getId(), medicationStatus);
    }
}
