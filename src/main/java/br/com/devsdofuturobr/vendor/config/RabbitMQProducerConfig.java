package br.com.devsdofuturobr.vendor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

    // Environment variables for queue, exchange, and routing key
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue() {
        // Creating the RabbitMQ Queue dynamically from env variables
        return new Queue(queueName, true); // Durable queue
    }

    @Bean
    public DirectExchange exchange() {
        // Creating a Direct Exchange dynamically from env variables
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        // Binding the queue with the exchange using a routing key dynamically
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}