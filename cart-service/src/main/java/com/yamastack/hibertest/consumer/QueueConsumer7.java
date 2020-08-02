package com.yamastack.hibertest.consumer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yamastack.hibertest.dto.NotificationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer7 {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "messagequeue3")
    public void receiveMessage(String message) {
        logger.info("Received (String) " + message);
        processMessage(message);
    }

    private void processMessage(String message) {
        try {
            NotificationRequestDTO dto = new ObjectMapper().readValue(message, NotificationRequestDTO.class);
            System.out.println("Queue7:" + dto.getCount() + "," + dto.getMessage() + "," + dto.getColor());
        } catch (JsonParseException e) {
            logger.warn("Bad JSON in message: " + message);
        } catch (JsonMappingException e) {
            logger.warn("cannot map JSON to NotificationRequest: " + message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
