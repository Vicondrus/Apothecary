package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.MedicationPlanDetailsDto;
import com.ds.project.apothecary.enums.MedicationStatus;
import com.ds.project.apothecary.services.MedicationDetailsService;
import com.ds.project.apothecary.services.implementations.Registry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

@Component
@FxmlView("main-stage.fxml")
public class MainController implements Initializable {

    private final MedicationDetailsService medicationDetailsService;

    @FXML
    public Label patientId;

    @FXML
    public Label medicationDownloadedAt;

    @FXML
    private ListView<MedicationPlanDetailsDto> medicationListView;

    @FXML
    private Label timerLabel;

    private Date currentDate = new Date();

    private Date lastDownloadedAt = new Date();

    public MainController(
            MedicationDetailsService medicationDetailsService) {
        this.medicationDetailsService = medicationDetailsService;
    }

    private void refresh() {
        medicationListView.getSelectionModel()
                .setSelectionMode(SelectionMode.SINGLE);

        String time = this.currentDate.toString().substring(11, 19);
        Integer hours = Integer.parseInt(time.substring(0, 2));
        if (Registry.getMedicationPlanDto() == null){
            return;
        }

        medicationDetailsService
                .setPreviousMedicationsAsNotTaken(this.currentDate, hours);
        List<MedicationPlanDetailsDto> list =
                medicationDetailsService.getMedicationsForGivenHour(hours);

        if (!list.isEmpty()) {
            medicationListView.getItems().setAll(list);
        }else{
            medicationListView.getItems().clear();
        }

        if (this.lastDownloadedAt.getDate() != this.currentDate.getDate()) {
            this.lastDownloadedAt = this.currentDate;
            medicationDownloadedAt.setText(lastDownloadedAt.toString());
            medicationDetailsService
                    .setMedicationPlanDtoForDate(this.lastDownloadedAt);
        }
    }

    private void updateTimerLabel() {
        Date current = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(this.currentDate);
        c.set(Calendar.MINUTE, current.getMinutes());
        c.set(Calendar.SECOND, current.getSeconds());
        this.currentDate = c.getTime();
        String time = this.currentDate.toString().substring(11, 19);
        Integer hours = Integer.parseInt(time.substring(0, 2));
        Integer minutes = Integer.parseInt(time.substring(3, 5));
        Integer seconds = Integer.parseInt(time.substring(6, 8));
        timerLabel.setText(String.format("%02d", hours) + ":" + String.format(
                "%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        medicationListView.getSelectionModel()
                .setSelectionMode(SelectionMode.SINGLE);
        resetModifier();
        refresh();
        patientId.setText(Registry.getPatientId().toString());
        medicationDownloadedAt.setText(lastDownloadedAt.toString());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(MainController.this::updateTimerLabel);
            }
        }, 0, 1000);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(MainController.this::refresh);
            }
        }, (long) 5 * 60 * 1000, (long) 5 * 60 * 1000);
    }

    public void resetModifier() {
        Date current = new Date();
        Calendar c = Calendar.getInstance();
        this.currentDate = c.getTime();
        c.setTime(current);
        refresh();
        updateTimerLabel();
    }

    public void addModifier() {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, 1);
        this.currentDate = c.getTime();
        refresh();
        updateTimerLabel();
    }

    public void subtractModifier() {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, -1);
        this.currentDate = c.getTime();
        refresh();
        updateTimerLabel();
    }

    public void setTaken() {
        MedicationPlanDetailsDto medicationPlanDetailsDto =
                medicationListView.getSelectionModel().getSelectedItem();
        if (medicationPlanDetailsDto == null){
            return;
        }
        medicationDetailsService.setMedicationStatus(currentDate,
                medicationPlanDetailsDto, MedicationStatus.TAKEN);
        refresh();
    }
}
