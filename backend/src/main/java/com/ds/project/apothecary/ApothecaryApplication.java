package com.ds.project.apothecary;

import com.ds.project.apothecary.services.PillBoxService;
import com.ds.project.apothecary.services.implementations.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Apothecary application.
 */
@SpringBootApplication
public class ApothecaryApplication implements RabbitListenerConfigurer {

    /**
     * The Topic exchange name.
     */
    static final String TOPIC_EXCHANGE_NAME = "apothecary-exchange";
    /**
     * The Queue name.
     */
    static final String QUEUE_NAME = "sensor-activities-queue";
    private final Receiver receiver;
    /**
     * Account service http invoker service exporter.
     *
     * @return the http invoker service exporter
     */
    private final PillBoxService pillBoxService;

    /**
     * Instantiates a new Apothecary consumer application.
     *
     * @param receiver the receiver
     */
    public ApothecaryApplication(Receiver receiver,
                                 PillBoxService pillBoxService) {
        this.receiver = receiver;
        this.pillBoxService = pillBoxService;
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(ApothecaryApplication.class, args);
    }

    /**
     * Connection factory connection factory.
     *
     * @return the connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory();
        connectionFactory.setHost("roedeer.rmq.cloudamqp.com");
        connectionFactory.setVirtualHost("bhkvvipl");
        connectionFactory.setUsername("bhkvvipl");
        connectionFactory.setPassword("8xp9j8hFYhykEKHwOdIyhjHByGqOvtis");
        connectionFactory.setRequestedHeartBeat(30);
        connectionFactory.setConnectionTimeout(30000);
        return connectionFactory;
    }

    /**
     * Queue queue.
     *
     * @return the queue
     */
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * Exchange topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /**
     * Binding binding.
     *
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("activity.#");
    }

    /**
     * Jackson 2 converter mapping jackson 2 message converter.
     *
     * @return the mapping jackson 2 message converter
     */
    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        return new MappingJackson2MessageConverter();
    }

    /**
     * Handler method factory default message handler method factory.
     *
     * @return the default message handler method factory
     */
    @Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory =
                new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(handlerMethodFactory());
    }

    /**
     * Cors configurer web mvc configurer.
     *
     * @return the web mvc configurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowCredentials(true)
                        .allowedOrigins("*").allowedMethods("*");
            }
        };
    }

    @Bean(name = "/pillbox") HttpInvokerServiceExporter accountService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(pillBoxService);
        exporter.setServiceInterface(PillBoxService.class);
        return exporter;
    }
}
