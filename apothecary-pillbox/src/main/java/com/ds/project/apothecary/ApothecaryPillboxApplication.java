package com.ds.project.apothecary;

import com.ds.project.apothecary.services.PillBoxService;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@SpringBootApplication
public class ApothecaryPillboxApplication {

    private final static String SERVICE_URL = "https://apothecary-backend" +
            ".herokuapp.com/pillbox";

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl(SERVICE_URL);
        invoker.setServiceInterface(PillBoxService.class);
        return invoker;
    }

}
