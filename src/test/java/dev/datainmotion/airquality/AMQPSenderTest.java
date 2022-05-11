package dev.datainmotion.airquality;

import dev.datainmotion.airquality.config.AMQPConfig;
import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.service.AMQPService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = {
                AMQPConfig.class,
                AMQPService.class,
        }
)
public class AMQPSenderTest {
    private static final Logger log = LoggerFactory.getLogger(AMQPSenderTest.class);

    @Autowired
    private AMQPService amqpService;

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
        amqpService.sendObservation(createTestObservation());
    }
}