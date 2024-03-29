package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Activity dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityDto {

    private Long patientId;

    private String activity;

    private Long start;

    private Long end;

    /**
     * Gets minutes duration.
     *
     * @return the minutes duration
     */
    public double getMinutesDuration() {
        double difference = (double) (end - start);
        double seconds = difference / 1000;
        return seconds / 60;
    }

    /**
     * Gets hours duration.
     *
     * @return the hours duration
     */
    public double getHoursDuration() {
        double difference = (double) (end - start);
        double seconds = difference / 1000;
        return seconds / 60 / 60;
    }
}
