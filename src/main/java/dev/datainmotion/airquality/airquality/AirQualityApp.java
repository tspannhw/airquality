package dev.datainmotion.airquality.airquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.datainmotion.airquality.airquality.model.Observation;
import dev.datainmotion.airquality.airquality.service.AirQualityService;
import java.util.List;

import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/** 
 * example spring boot app to read rest feed send to Pulsar 
 * 
*/
@SpringBootApplication
public class AirQualityApp implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AirQualityApp.class);

	public static void main(String[] args) {
		SpringApplication.run(AirQualityApp.class, args);
	}

    @Autowired
    private AirQualityService airQualityService;

	@Override
    public void run(String... args) {
		List<Observation> obsList = airQualityService.fetchCurrentObservation();
		MessageId msgId = null;
		for (Observation observation2 : obsList) {
			msgId = airQualityService.sendObservation(observation2);
			log.debug(msgId.toString());
		}
    }
}