package dev.datainmotion.airquality.config;
import dev.datainmotion.airquality.model.Observation;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${producer.name:producername}")
    String producerName;

    @Value("${kafka.topic.name:airqualitykop}")
    String topicName;

    @Value("${kafka.bootstrapAddress}")
    String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Observation> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Observation> kafkaTemplate() {
        KafkaTemplate<String, Observation> kafkaTemplate = new KafkaTemplate<String, Observation>(producerFactory());
        kafkaTemplate.setDefaultTopic(topicName);
        return kafkaTemplate;
    }
}