package com.yamastack.hibertest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yamastack.hibertest.dto.NotificationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public QueueProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceDirect(NotificationRequestDTO notificationDTO) throws Exception {
        System.out.println("Storing notification...");
        rabbitTemplate.setExchange("tut.direct");
        rabbitTemplate.convertAndSend(notificationDTO.getColor(), new ObjectMapper().writeValueAsString(notificationDTO));
        System.out.println("Notification stored in queue sucessfully");
    }
}
