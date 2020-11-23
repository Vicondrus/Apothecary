package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.NotificationDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * The type Socket controller.
 */
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SocketController {

    private final SimpMessagingTemplate template;

    /**
     * Instantiates a new Socket controller.
     *
     * @param template the template
     */
    public SocketController(
            SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Send.
     *
     * @param caregiver    the caregiver
     * @param notification the notification
     */
    public void send(String caregiver,
                     NotificationDto notification) {
        this.template.convertAndSend("/anomalous-activities/" + caregiver,
                notification);
    }
}
