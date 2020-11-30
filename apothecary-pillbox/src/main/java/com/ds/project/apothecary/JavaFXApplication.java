package com.ds.project.apothecary;

import com.ds.project.apothecary.controllers.MainController;
import com.ds.project.apothecary.services.PillBoxService;
import com.ds.project.apothecary.services.implementations.Registry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

public class JavaFXApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        applicationContext = new SpringApplicationBuilder()
                .sources(ApothecaryPillboxApplication.class)
                .run(args);
        Registry.setMedicationPlanDto(
                applicationContext.getBean(PillBoxService.class)
                        .retrieveMedicationPlanForToday(new Date(), 35L));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
