package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.util.DataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * send rabbitmq / amqp messages
 */
@Service
public class AMQPService {
        private static final Logger log = LoggerFactory.getLogger(dev.datainmotion.airquality.service.AMQPService.class);

        @Value("${amqp.topic:amqp-airquality}")
        String topicName;

        @Autowired
        private RabbitTemplate rabbitTemplate;

        public void sendObservation(Observation observation) {
                if (observation == null) {
                        log.error("Observation is null");
                        return;
                }
                log.debug("topic {}", topicName);
                rabbitTemplate.convertAndSend(topicName,
                        DataUtility.serializeToJSON(observation));
        }
}