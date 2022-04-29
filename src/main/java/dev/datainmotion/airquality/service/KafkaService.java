package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

/**
 * service for kafka messages
 */
@Service
public class KafkaService {
    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, Observation> kafkaTemplate;

    @Value("${kafka.topic.name:airqualitykafka}")
    String topicName;

    /**
     * send message to kafka
     * @param message Observation
     */
    public void sendMessage(Observation message) {
        UUID uuidKey = UUID.randomUUID();

        ProducerRecord<String, Observation> producerRecord = new ProducerRecord<>(topicName,
                uuidKey.toString(),
                message);

        kafkaTemplate.send(producerRecord);
//        ListenableFuture<SendResult<String, Observation>> future =
//                kafkaTemplate.send(producerRecord);
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Observation>>() {
//            @Override
//            public void onSuccess(SendResult<String, Observation> result) {
//                System.out.println("Sent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=["
//                        + message + "] due to : " + ex.getMessage());
//            }
//        });
    }
}
