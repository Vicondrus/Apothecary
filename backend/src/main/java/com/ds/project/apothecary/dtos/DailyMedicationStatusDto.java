package com.ds.project.apothecary.dtos;

import com.ds.project.apothecary.entities.DailyMedicationStatus;
import com.ds.project.apothecary.enums.MedicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyMedicationStatusDto implements Serializable {

    private static final long serialVersionUID = -5500830787844690173L;
    private Long id;

    private MedicationPlanDetailsDto
            medicationPlanDetails;

    private MedicationStatus status;

    private Date date;

    public DailyMedicationStatusDto(
            DailyMedicationStatus dailyMedicationStatus) {
        this.id = dailyMedicationStatus.getId();
        this.medicationPlanDetails =
                new MedicationPlanDetailsDto(
                        dailyMedicationStatus.getMedicationPlanDetails());
        this.status = dailyMedicationStatus.getStatus();
        this.date = dailyMedicationStatus.getDate();
    }
}
