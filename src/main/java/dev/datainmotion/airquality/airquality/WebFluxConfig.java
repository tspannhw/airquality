package dev.datainmotion.airquality.airquality;

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

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
	private static final String OFF = "off";

	Logger log = LoggerFactory.getLogger(WebFluxConfig.class);

	@Value("${airnowapi.url:http://localhost:8080}")
	String airnowapi;

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
	 * @param brokerUrl url of pulsar
	 * @return pulsarClient
	 */
	@Bean
	public org.apache.pulsar.client.api.PulsarClient pulsarClient() {
		PulsarClient client = null;

		if (securityMode.equalsIgnoreCase(OFF)) {
			try {
				client = PulsarClient.builder().serviceUrl(pulsarUrl).build();
			} catch (PulsarClientException e) {
				log.error("bad url", e);
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
					log.error("bad url", e);
				}
			} catch (PulsarClientException e) {
				log.error("pulsar connection failed", e);
				client = null;
			}
		}

		return client;
	}

	@Bean
	public WebClient getWebClient() {
		return WebClient.builder()
				.baseUrl(airnowapi)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}