package com.notification_service.adapter.in.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queueCollectionUpdated() {
        return new Queue("collection.updated");
    }

    @Bean
    public Queue queueCollectionCreated() {
        return new Queue("collection.created");
    }
    @Bean
    public Queue queueCollectionDeleted() {
        return new Queue("collection.deleted");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("collection_service_exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueCollectionUpdated()).to(exchange()).with("collection.updated.#");
    }

}

