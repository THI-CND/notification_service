package com.notification_service.adapter.in.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("collection_service_exchange");
    }

    @Bean
    public List<Binding> bindings(TopicExchange exchange) {
        List<String> queueNames = List.of("collection.updated", "collection.created", "collection.deleted");

        return queueNames.stream()
                .map(queueName -> {
                    Queue queue = new Queue(queueName);
                    return BindingBuilder.bind(queue).to(exchange).with(queueName + ".#");
                })
                .toList();
    }
    /*
    @Bean
    public Queue queueCollectionUpdated() {
        return new Queue("collection.updated");
    }

    @Bean
    public Binding bindingCollectionUpdated() {
        return BindingBuilder.bind(queueCollectionUpdated()).to(exchange()).with("collection.updated.#");
    }

     */
}

