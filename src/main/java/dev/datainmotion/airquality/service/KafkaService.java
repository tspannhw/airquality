package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.util.DataUtility;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * service for kafka messages
 */
@Service
public class KafkaService {
    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name:airqualitykafka2}")
    String topicName;

    /**
     * send message to kafka
     * @param message Observation
     */
    @Async
    public void sendMessage(Observation message) {
        UUID uuidKey = UUID.randomUUID();

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName,
                uuidKey.toString(),
                DataUtility.serializeToJSON(message));

       kafkaTemplate.send(producerRecord);

       log.info("KAFKA MSG SENT");
   }
}