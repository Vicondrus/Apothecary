package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.MedicationPlanDto;
import com.ds.project.apothecary.entities.MedicationPlan;
import com.ds.project.apothecary.entities.MedicationPlanDetails;
import com.ds.project.apothecary.repositories.MedicationPlanRepository;
import com.ds.project.apothecary.repositories.MedicationRepository;
import com.ds.project.apothecary.repositories.PatientRepository;
import com.ds.project.apothecary.services.MedicationPlanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Medication plan service.
 */
@Service
public class MedicationPlanServiceImpl
        implements MedicationPlanService {

    /**
     * The Medication plan repository.
     */
    private final MedicationPlanRepository
            medicationPlanRepository;

    /**
     * The Patient repository.
     */
    private final PatientRepository
            patientRepository;

    /**
     * The Medication repository.
     */
    private final MedicationRepository
            medicationRepository;

    /**
     * Instantiates a new Medication plan service.
     *
     * @param pMedicationPlanRepository the medication plan repository
     * @param pPatientRepository        the patient repository
     * @param pMedicationRepository     the medication repository
     */
    public MedicationPlanServiceImpl(
            final MedicationPlanRepository pMedicationPlanRepository,
            final PatientRepository pPatientRepository,
            final MedicationRepository pMedicationRepository) {
        this.medicationPlanRepository =
                pMedicationPlanRepository;
        this.patientRepository =
                pPatientRepository;
        this.medicationRepository =
                pMedicationRepository;
    }

    /**
     * Find all for patient id list.
     *
     * @param id the id
     * @return the list
     */
    @Override
    public List<MedicationPlanDto> findAllForPatientId(final Long id) {
        return medicationPlanRepository.getAllByPatientId(id).stream()
                .map(MedicationPlanDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Create medication plan dto.
     *
     * @param patientId         the patient id
     * @param medicationPlanDto the medication plan dto
     * @return the medication plan dto
     */
    @Override
    public MedicationPlanDto create(final Long patientId,
                                    final MedicationPlanDto medicationPlanDto) {
        MedicationPlan
                medicationPlan =
                MedicationPlan.builder()
                        .patient(patientRepository.getOne(patientId))
                        .periodStart(medicationPlanDto.getPeriodStart())
                        .periodEnd(medicationPlanDto.getPeriodEnd())
                        .build();

        Set<MedicationPlanDetails>
                medicationPlanDetailsSet =
                medicationPlanDto.getMedications().stream()
                        .map(
                                medicationPlanDetailsDto -> {
                                    MedicationPlanDetails
                                            medicationPlanDetails =
                                            new MedicationPlanDetails();

                                    medicationPlanDetails.setIntakeInterval(
                                            medicationPlanDetailsDto
                                                    .getIntakeInterval());
                                    medicationPlanDetails
                                            .setMedicationPlan(medicationPlan);
                                    medicationPlanDetails.setMedication(
                                            medicationRepository.getOne(
                                                    medicationPlanDetailsDto
                                                            .getMedication()
                                                            .getId()));

                                    return medicationPlanDetails;
                                })
                        .collect(Collectors.toSet());

        medicationPlan.setMedicationPlanDetailsSet(medicationPlanDetailsSet);

        return new MedicationPlanDto(
                medicationPlanRepository.save(medicationPlan));
    }
}
