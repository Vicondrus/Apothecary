package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.NotificationDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SocketController {

    private final SimpMessagingTemplate template;

    public SocketController(
            SimpMessagingTemplate template) {
        this.template = template;
    }

    public void send(String caregiver,
                     NotificationDto notification) {
        this.template.convertAndSend("/anomalous-activities/" + caregiver,
                notification);
    }
}
