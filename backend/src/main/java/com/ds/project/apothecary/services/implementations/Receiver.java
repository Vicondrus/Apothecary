package com.ds.project.apothecary.services.implementations;

import com.ds.project.apothecary.dtos.ActivityDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    private final ActivityProcessor activityProcessor;

    public Receiver(
            ActivityProcessor activityProcessor) {
        this.activityProcessor = activityProcessor;
    }

    /**
     * Receive message.
     *
     * @param activity the activity
     */
    @RabbitListener(queues = "sensor-activities-queue")
    public void receiveMessage(ActivityDto activity) {
        activityProcessor.processActivity(activity);
    }

}
