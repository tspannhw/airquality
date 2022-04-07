package dev.datainmotion.airquality.airquality.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.datainmotion.airquality.airquality.model.Observation;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.UUID;

/**
 * 
 */
@Service
public class AirQualityService {
    private static final Logger log = LoggerFactory.getLogger(AirQualityService.class);

    @Autowired
    WebClient webClient;

    @Autowired
    PulsarClient pulsarClient;

    @Value("${topic.name:airquality}")
	String topicName;

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

        // move to bean
        ProducerBuilder<Observation> producerBuilder = pulsarClient.newProducer(JSONSchema.of(Observation.class))
                .topic(topicName)
                .producerName("airquality").sendTimeout(60, TimeUnit.SECONDS);

        Producer<Observation> producer = null;
        try {
            producer = producerBuilder.create();
        } catch (PulsarClientException e1) {
            e1.printStackTrace();
        }
        if ( producer == null) {
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

        try {
            producer.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return msgID;
    }

    /**
     * 
     * @return list of observations from json
     */
    public List<Observation> fetchCurrentObservation() {

        List<Observation> obs = null;

        try {
            Flux<Observation> observationFlux = webClient
                    .get()
                    .retrieve()
                    .bodyToFlux(Observation.class)
                    .timeout(Duration.ofMillis(10_000));

            obs = observationFlux
                    .collect(Collectors.toList())
                    .share().block();
        } catch (Throwable t) {
            log.error("fetch failed", t);
        }

        return obs;
    }
}