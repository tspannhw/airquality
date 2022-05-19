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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
	private static final String DEFAULT_ZIPCODE = "08520";

	Logger log = LoggerFactory.getLogger(WebFluxConfig.class);

	@Value("${airnowapi.url:http://localhost:8080}")
	String airnowapi;

	@Value("${zipcodes}")
	String[] arrayOfStrings;

	/** todo move array list to spring properties */
	public String getZipCode() {
		Random rand = new Random();
		String zipCode = null;

		try {
			zipCode= arrayOfStrings[rand.nextInt(arrayOfStrings.length)];
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ( zipCode == null || zipCode.trim().length() <= 0) {
			zipCode = arrayOfStrings[0];
		}

		if ( zipCode == null || zipCode.trim().length() <= 0) {
			zipCode = DEFAULT_ZIPCODE;
		}

		return zipCode;
	}
	@Bean
	public WebClient getWebClient() {
		return WebClient.builder()
				.baseUrl(airnowapi + getZipCode())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}