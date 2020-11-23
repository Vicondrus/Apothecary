package com.ds.project.apothecary;

import com.ds.project.apothecary.services.PillBoxService;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@SpringBootApplication
public class ApothecaryPillboxApplication {

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("https://apothecary-backend.herokuapp" +
                ".com/pillbox");
        invoker.setServiceInterface(PillBoxService.class);
        return invoker;
    }

}
