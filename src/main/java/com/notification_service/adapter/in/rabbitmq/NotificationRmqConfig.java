package com.notification_service.adapter.in.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class NotificationRmqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.collection}")
    private String collectionQueueName;

    @Value("${rabbitmq.queue.review}")
    private String reviewQueueName;

    @Value("${rabbitmq.queue.user}")
    private String userQueueName;

    @Value("#{'${rabbitmq.routing.keys.collection}'.split(',')}")
    private List<String> collectionRoutingKeys;

    @Value("#{'${rabbitmq.routing.keys.review}'.split(',')}")
    private List<String> reviewRoutingKeys;

    @Value("#{'${rabbitmq.routing.keys.user}'.split(',')}")
    private List<String> userRoutingKeys;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue collectionQueue() {
        return new Queue(collectionQueueName, true);
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue(reviewQueueName, true);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName, true);
    }

    @Bean
    public Declarables bindings() {
        List<Binding> collectionBindings = collectionRoutingKeys.stream()
                .map(routingKey -> new Binding(collectionQueueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null))
                .toList();

        List<Binding> reviewBindings = reviewRoutingKeys.stream()
                .map(routingKey -> new Binding(reviewQueueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null))
                .toList();

        List<Binding> userBindings = userRoutingKeys.stream()
                .map(routingKey -> new Binding(userQueueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null))
                .toList();

        return new Declarables(Stream.of(collectionBindings, reviewBindings, userBindings)
                .flatMap(List::stream)
                .toList());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

