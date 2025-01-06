package com.notification_service.adapter.in.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NotificationRmqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.keys}")
    private List<String> routingKeys;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Declarables bindings() {
        List<Binding> bindings = routingKeys.stream()
                .map(routingKey -> new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null))
                .toList();
        return new Declarables(bindings);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

