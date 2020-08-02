package com.yamastack.hibertest;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }

    // @Value("${fanout.exchange}")
    // private String fanoutExchange;

    @Bean
    Queue queue3() {
        return new Queue("messagequeue3", true);
    }

    @Bean
    public Binding bindingDirect1(DirectExchange direct,
                             Queue queue3) {
        return BindingBuilder.bind(queue3)
                .to(direct)
                .with("red");
    }
}
