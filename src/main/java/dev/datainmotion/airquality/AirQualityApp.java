package dev.datainmotion.airquality;

import dev.datainmotion.airquality.service.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dev.datainmotion.airquality.model.Observation;

import java.net.http.WebSocket;
import java.util.Calendar;
import java.util.List;
import org.apache.pulsar.client.api.MessageId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


/** 
 * example spring boot app to read rest feed send to Pulsar 
 * 
*/

@SpringBootApplication
@EnableScheduling
public class AirQualityApp implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(AirQualityApp.class);

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AirQualityApp.class, args);
	}

    @Autowired
    private AirQualityService airQualityService;

	@Autowired
	private MQTTService mqttService;

	@Autowired
	private PulsarService pulsarService;

	@Autowired
	private AMQPService amqpService;

	/**
	 * get rows
	 */
	private void getRows() {
		List<Observation> obsList = airQualityService.fetchCurrentObservation();
		MessageId msgId = null;
		for (Observation observation2 : obsList) {
			try {
				msgId = pulsarService.sendObservation(observation2);
				log.info(msgId.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				mqttService.publish(observation2);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			try {
				amqpService.sendObservation(observation2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Scheduled(initialDelay = 0, fixedRate = 10000)
	public void repeatRun()
	{
		log.debug("Repeat Run. Current time is :: " + Calendar.getInstance().getTime());
		getRows();
	}

	@Override
    public void run(String... args) {
    }
}