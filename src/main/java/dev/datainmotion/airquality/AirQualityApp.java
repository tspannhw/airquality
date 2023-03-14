package dev.datainmotion.airquality;

import dev.datainmotion.airquality.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dev.datainmotion.airquality.model.Observation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
    private KafkaService kafkaService;

    /**
     * get rows
     */
    private void getRows() {
        List<Observation> obsList = airQualityService.fetchCurrentObservation();
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
                kafkaService.sendMessage(observation2);
                log.info("Kafka message sent.");
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