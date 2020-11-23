package com.ds.project.apothecary.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Notification dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationDto {

    /**
     * The Activity.
     */
    ActivityDto activity;

    /**
     * The Patient.
     */
    BareUserDto patient;

}
