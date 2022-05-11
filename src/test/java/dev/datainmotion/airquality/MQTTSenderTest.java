package dev.datainmotion.airquality;

import dev.datainmotion.airquality.config.MQTTConfig;
import dev.datainmotion.airquality.config.PulsarConfig;
import dev.datainmotion.airquality.config.PulsarProducerConfig;
import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.service.MQTTService;
import org.apache.pulsar.client.api.MessageId;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

@SpringBootTest(
        classes = {
                MQTTConfig.class,
                MQTTService.class
        }
)
public class MQTTSenderTest {
    private static final Logger log = LoggerFactory.getLogger(MQTTSenderTest.class);

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private MQTTService mqttService;

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
        try {
            mqttService.publish(createTestObservation());
        } catch (MqttException e) {
            e.printStackTrace();
            fail();
        }
    }
}