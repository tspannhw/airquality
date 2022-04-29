package dev.datainmotion.airquality.config;

import dev.datainmotion.airquality.model.Observation;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration 
public class PulsarConfig {
 
    @Value("${producer.name:producername}")
	String producerName;

	@Value("${topic.name:airquality}")
	String topicName;

	@Autowired
    PulsarClient pulsarClient;
    
    @Bean
	public Producer<Observation>  getProducer() {
        ProducerBuilder<Observation> producerBuilder = pulsarClient.newProducer(JSONSchema.of(Observation.class))
                .topic(topicName)
                .producerName(producerName).sendTimeout(60, TimeUnit.SECONDS);

		Producer<Observation> producer = null;
		try {
			producer = producerBuilder.create();
		} catch (PulsarClientException e1) {
			e1.printStackTrace();
		}

		return producer;
	}
}