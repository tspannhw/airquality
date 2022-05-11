package dev.datainmotion.airquality.config;

import dev.datainmotion.airquality.model.Observation;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Configuration 
public class PulsarConfig {
	private static final Logger log = LoggerFactory.getLogger(PulsarConfig.class);
	private static final String PULSAR_CONNECTION_FAILED = "pulsar connection failed";
	private static final String BAD_URL = "bad url";
	private static final String OFF = "off";

	@Value("${pulsar.service.url:pulsar://localhost:6650}")
	String pulsarUrl;

	@Value("${security.mode:off}")
	String securityMode;

	@Value("${pulsar.oauth2.audience:urn:sn:pulsar:sndemo:demo-cluster}")
	String audience;

	@Value("${pulsar.oauth2.credentials-url:file:///Users/tspann/Downloads/sndemo-tspann.json}")
	String credentialsUrl;

	@Value("${pulsar.oauth2.issuer-url:https://auth.streamnative.cloud/}")
	String issuerUrl;

	/**
	 * @return pulsarClient
	 */
	@Bean
	public org.apache.pulsar.client.api.PulsarClient pulsarClient() {
		PulsarClient client = null;

		if (securityMode.equalsIgnoreCase(OFF)) {
			try {
				client = PulsarClient.builder().serviceUrl(pulsarUrl).build();
			} catch (PulsarClientException e) {
				log.error(BAD_URL, e);
				client = null;
			}
		} else {
			try {
				try {
					client = PulsarClient.builder()
							.serviceUrl(pulsarUrl)
							.authentication(
									AuthenticationFactoryOAuth2.clientCredentials(
											new URL(issuerUrl),
											new URL(credentialsUrl),
											audience))
							.build();
				} catch (MalformedURLException e) {
					log.error(BAD_URL, e);
				}
			} catch (PulsarClientException e) {
				log.error(PULSAR_CONNECTION_FAILED, e);
				client = null;
			}
		}

		return client;
	}
}