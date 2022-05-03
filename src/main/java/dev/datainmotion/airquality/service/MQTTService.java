package dev.datainmotion.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.util.DataUtility;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * service for mqtt messages
 */
@Service
public class MQTTService {

    @Value("${mqtt.topic:airqualitymqtt}")
    String topicName;

    @Autowired
    private IMqttClient mqttClient;


    /**
     * publish messages to mqtt (pulsar)
     * @param payload
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(final Observation payload)
            throws MqttPersistenceException, MqttException {
        System.out.println("Publish MQTT");
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(DataUtility.serialize(payload));
        mqttMessage.setQos(0);
        mqttMessage.setRetained(true);
        mqttClient.publish(topicName, mqttMessage);
        System.out.println("MQTT sent" + mqttMessage.getId());
    }
}
