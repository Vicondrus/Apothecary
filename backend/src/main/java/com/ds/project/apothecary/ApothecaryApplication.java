package com.ds.project.apothecary;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Apothecary application.
 */
@SpringBootApplication
public class ApothecaryApplication implements RabbitListenerConfigurer {

    private final Receiver receiver;

    static final String TOPIC_EXCHANGE_NAME = "apothecary-exchange";

    static final String QUEUE_NAME = "sensor-activities-queue";

    /**
     * Instantiates a new Apothecary consumer application.
     *
     * @param receiver the receiver
     */
    public ApothecaryApplication(Receiver receiver) {
        this.receiver = receiver;
    }

    public static void main(final String[] args) {
        SpringApplication.run(ApothecaryApplication.class, args);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("roedeer.rmq.cloudamqp.com");
        connectionFactory.setVirtualHost("bhkvvipl");
        connectionFactory.setUsername("bhkvvipl");
        connectionFactory.setPassword("8xp9j8hFYhykEKHwOdIyhjHByGqOvtis");
        connectionFactory.setRequestedHeartBeat(30);
        connectionFactory.setConnectionTimeout(30000);
        return connectionFactory;
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

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
}
