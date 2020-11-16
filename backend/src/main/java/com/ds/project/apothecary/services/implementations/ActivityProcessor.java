package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.controllers.SocketController;
import com.ds.project.apothecary.dtos.ActivityDto;
import com.ds.project.apothecary.dtos.BareUserDto;
import com.ds.project.apothecary.dtos.NotificationDto;
import com.ds.project.apothecary.dtos.UserDto;
import com.ds.project.apothecary.entities.AnomalousActivity;
import com.ds.project.apothecary.entities.Patient;
import com.ds.project.apothecary.repositories.AnomalousActivityRepository;
import com.ds.project.apothecary.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class ActivityProcessor {

    private static final Logger
            LOGGER =
            LoggerFactory.getLogger(ActivityProcessor.class);
    private final SocketController socketController;
    private final AnomalousActivityRepository anomalousActivityRepository;
    private final PatientRepository patientRepository;
    private final double SLEEP_PERIOD_HOURS_ANOMALY = 7.0;
    private final double OUTDOOR_PERIOD_HOURS_ANOMALY = 5.0;
    private final double BATHROOM_PERIOD_MINUTES_ANOMALY = 30.0;

    public ActivityProcessor(
            AnomalousActivityRepository anomalousActivityRepository,
            PatientRepository patientRepository,
            SocketController socketController) {
        this.anomalousActivityRepository = anomalousActivityRepository;
        this.patientRepository = patientRepository;
        this.socketController = socketController;
    }

    private static boolean stringContainsItemFromList(String inputStr,
                                                      String[] items) {
        return Arrays.stream(items).anyMatch(inputStr.toLowerCase()::contains);
    }

    public void processActivity(ActivityDto activity) {
        Patient patient =
                patientRepository.findById(activity.getPatientId())
                        .orElse(null);
        if (patient != null) {
            boolean suspect = checkSuspectActivities(activity);
            if (suspect) {
                LOGGER.warn("Suspicious activity for patient {}: {} for " +
                                "{} minutes",
                        patient.getUsername(), activity.getActivity(),
                        activity.getMinutesDuration());
                AnomalousActivity anomalousActivity =
                        getAnomalousActivityEntity(activity, patient);
                anomalousActivityRepository.save(anomalousActivity);
                socketController.send(patient.getCaregiver().getUsername(),
                        NotificationDto.builder().activity(activity).
                                patient(new BareUserDto(patient)).build());
            } else {
                LOGGER.info("Normal activity for patient {}: {} for {} minutes",
                        patient.getUsername(), activity.getActivity(),
                        activity.getMinutesDuration());
            }
        }
    }

    private AnomalousActivity getAnomalousActivityEntity(ActivityDto activity
            , Patient patient) {
        return AnomalousActivity.builder()
                .activity(activity.getActivity())
                .patient(patient)
                .start_time(new Date(activity.getStart()))
                .end_time(new Date(activity.getEnd()))
                .build();
    }

    private boolean checkSuspectActivities(ActivityDto activity) {
        if (checkSuspectBathroom(activity)) {
            return true;
        }
        if (checkSuspectOutdoor(activity)) {
            return true;
        }
        return checkSuspectSleep(activity);
    }

    private boolean checkSuspectSleep(ActivityDto activity) {
        if (stringContainsItemFromList(activity.getActivity(),
                new String[]{"sleep"})) {
            return activity.getHoursDuration() > SLEEP_PERIOD_HOURS_ANOMALY;
        }
        return false;
    }

    private boolean checkSuspectOutdoor(ActivityDto activity) {
        if (stringContainsItemFromList(activity.getActivity(),
                new String[]{"outdoor", "leaving", "leave"})) {
            return activity.getHoursDuration() > OUTDOOR_PERIOD_HOURS_ANOMALY;
        }
        return false;
    }

    private boolean checkSuspectBathroom(ActivityDto activity) {
        if (stringContainsItemFromList(activity.getActivity(),
                new String[]{"groom", "shower", "bath", "toilet"})) {
            return activity.getHoursDuration() >
                    BATHROOM_PERIOD_MINUTES_ANOMALY;
        }
        return false;
    }
}
