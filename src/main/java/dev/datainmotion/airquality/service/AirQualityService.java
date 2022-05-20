package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.util.DataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 */
@Service
public class AirQualityService {
    private static final Logger log = LoggerFactory.getLogger(AirQualityService.class);

    @Autowired
    WebClient webClient;

    @Autowired
    DataUtility dataUtility;
    
    /**
     * 
     * @return list of observations from json
     */
    public List<Observation> fetchCurrentObservation() {
        List<Observation> obs = null;

        try {
            webClient.mutate().baseUrl(dataUtility.getURL()) ;

            log.error(dataUtility.getURL());

            Flux<Observation> observationFlux = webClient
                    .get()
                    .retrieve()
                    .bodyToFlux(Observation.class)
                    .timeout(Duration.ofMillis(10_000));

            obs = observationFlux
                    .collect(Collectors.toList())
                    .share().block();
        } catch (Throwable t) {
            log.error("fetch failed", t);
        }

        return obs;
    }
}