package dev.datainmotion.airquality;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.service.AirQualityService;
import dev.datainmotion.airquality.service.PulsarService;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AirQualityAppTests {

    @Autowired
    PulsarClient pulsarClient;

    @Autowired
    Producer<Observation> producer;

    @Autowired
    private PulsarService pulsarService;

    @Test
    void contextLoads() {
    }
}