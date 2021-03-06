package dev.datainmotion.airquality;

import dev.datainmotion.airquality.config.PulsarConfig;
import dev.datainmotion.airquality.config.PulsarProducerConfig;
import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.service.AirQualityService;
import dev.datainmotion.airquality.service.PulsarService;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.testng.Assert.assertNotNull;


@SpringBootTest(
        classes = {
                PulsarConfig.class,
                PulsarProducerConfig.class,
                PulsarService.class
        }
)
public class AirQualityAppTests {
    private static final Logger log = LoggerFactory.getLogger(AirQualityAppTests.class);

    @Autowired
    PulsarClient pulsarClient;

    @Autowired
    Producer<Observation> producer;

    @Autowired
    private PulsarService pulsarService;

    private Observation createTestObservation() {
        Observation observation = new Observation();
        observation.setAqi(14);
        observation.setDateObserved("2022-05-011");
        observation.setHourObserved(11);
        observation.setLatitude(33.65);
        observation.setLocalTimeZone("EST");
        observation.setLongitude(-84.43);
        observation.setReportingArea("Atlanta");
        observation.setStateCode("GA");
        return observation;
    }
    @Test
    void contextLoads() {
        MessageId messageId = pulsarService.sendObservation(createTestObservation());
        assertNotNull(messageId);
        log.debug(String.valueOf(messageId));
    }
}