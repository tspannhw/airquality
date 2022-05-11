package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 *
 */
@Service
public class PulsarService {
    private static final Logger log = LoggerFactory.getLogger(PulsarService.class);

    @Autowired
    PulsarClient pulsarClient;

    @Autowired
    Producer<Observation> producer;

    /**
     * sendObservation to pulsar
     *
     * @param observation
     * @return MessageId
     */
    public MessageId sendObservation(Observation observation) {
        if (observation == null) {
            return null;
        }

        UUID uuidKey = UUID.randomUUID();
        MessageId msgID = null;
        try {
            msgID = producer.newMessage()
                    .key(uuidKey.toString())
                    .value(observation)
                    .send();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }

        return msgID;
    }
}
