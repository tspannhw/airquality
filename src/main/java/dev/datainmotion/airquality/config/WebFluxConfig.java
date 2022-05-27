package dev.datainmotion.airquality.config;

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

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
	Logger log = LoggerFactory.getLogger(WebFluxConfig.class);

	@Value("${airnowapi.url:http://localhost:8080}")
	String airnowapi;

	@Bean
	public WebClient getWebClient() {
		return WebClient.builder()
				.baseUrl(airnowapi + "08520")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
	@Bean
	public WebClient getWebClient(String zipCode) {
		return WebClient.builder()
				.baseUrl(airnowapi + zipCode)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}