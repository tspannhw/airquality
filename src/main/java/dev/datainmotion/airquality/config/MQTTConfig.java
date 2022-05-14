package dev.datainmotion.airquality.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfig {

    /**
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    /**
     *
     * @param clientId
     * @param hostname
     * @param port
     * @return mqtt client
     * @throws MqttException
     */
    @Bean
    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
                                  @Value("${mqtt.hostname}") String hostname,
                                  @Value("${mqtt.port}") int port) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId, persistence);
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }
}