package dev.datainmotion.airquality.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.datainmotion.airquality.model.Observation;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.UUID;

@Configuration 
public class PulsarProducerConfig {
 
    @Value("${producer.name:producername}")
	String producerName;

	@Value("${topic.name:airquality}")
	String topicName;

	@Autowired
    PulsarClient pulsarClient;
    
    @Bean
	public ProducerBuilder<Observation>  getProducer() {
        ProducerBuilder<Observation> producerBuilder = pulsarClient.newProducer(JSONSchema.of(Observation.class))
                .topic(topicName)
                .producerName(producerName).sendTimeout(60, TimeUnit.SECONDS);

		return producerBuilder;
	}

}
