package dev.datainmotion.airquality;

import dev.datainmotion.airquality.model.Reading;
import dev.datainmotion.airquality.repository.ReadingRepository;
import dev.datainmotion.airquality.service.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dev.datainmotion.airquality.model.Observation;

import java.util.List;
import java.util.Optional;

import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * example spring boot app to read rest feed send to Pulsar
 */
@SpringBootApplication
@EnableScheduling
public class AirQualityApp implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AirQualityApp.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AirQualityApp.class, args);
    }

    @Autowired
    private AirQualityService airQualityService;

    @Autowired
    private MQTTService mqttService;

    @Autowired
    private PulsarService pulsarService;

    @Autowired
    private AMQPService amqpService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    FeatureStoreService featureStoreService;

    /**
     * get rows
     */
    private void getRows() {
        List<Observation> obsList = airQualityService.fetchCurrentObservation();
        MessageId msgId = null;
        if (obsList == null || obsList.size() <= 0) {
            return;
        }
        for (Observation observation2 : obsList) {
            log.info("{}={} for {} {}",
                    observation2.getParameterName(),
                    observation2.getAqi(),
                    observation2.getStateCode(),
                    observation2.getReportingArea());
            try {
                msgId = pulsarService.sendObservation(observation2);
                log.info("PULSAR {}", msgId.toString());
            } catch (Exception e) {
                log.error("Pulsar Error", e);
            }

            if ( readingRepository != null) {
                try {
                    Optional<Reading> result = readingRepository.findByReadingid(msgId.toString());

                    // should find by max and set that
                    if ( result != null) {
                        log.info("Found existing {}", result.get().toString());

                        // add update method
                    }
                    else {
                        boolean isSaved = featureStoreService.saveObservation(observation2, msgId.toString());

                        log.info("Saved {}", isSaved);
                    }

                }
                catch(Exception x) {
                    log.error("ScyllaDB Error",x);
                }
            }
            try {
                mqttService.publish(observation2);
                log.info("MQTT");

            } catch (MqttException e) {
                log.error("MQTT Error", e);
            }
            try {
                amqpService.sendObservation(observation2);
                log.info("AMQP");
            } catch (Exception e) {
                log.error("AMQP Error", e);
            }
            try {
                kafkaService.sendMessage(observation2);
                log.info("KAFKA");
            } catch (Exception e) {
                log.error("Kafka Error", e);
            }
        }
    }

    @Scheduled(initialDelay = 0, fixedRate = 10000)
    public void repeatRun() {
        getRows();
    }

    @Override
    public void run(String... args) {
        getRows();
    }
}