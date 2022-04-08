package dev.datainmotion.airquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.service.AirQualityService;

import java.util.Calendar;
import java.util.List;

import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 
 * example spring boot app to read rest feed send to Pulsar 
 * 
*/
@SpringBootApplication
@EnableScheduling
public class AirQualityApp implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AirQualityApp.class);

	public static void main(String[] args) {
		SpringApplication.run(AirQualityApp.class, args);
	}

    @Autowired
    private AirQualityService airQualityService;

	/**
	 * get rows
	 */
	private void getRows() {
		List<Observation> obsList = airQualityService.fetchCurrentObservation();
		MessageId msgId = null;
		for (Observation observation2 : obsList) {
			msgId = airQualityService.sendObservation(observation2);
			log.debug(msgId.toString());
		}
	}

	@Scheduled(initialDelay = 10000, fixedRate = 10000)
	public void repeatRun()
	{
		log.debug("Current time is :: " + Calendar.getInstance().getTime());
		getRows();
	}

	@Override
    public void run(String... args) {
		log.debug("RUN ONCE Current time is :: " + Calendar.getInstance().getTime());
		getRows();
    }
}