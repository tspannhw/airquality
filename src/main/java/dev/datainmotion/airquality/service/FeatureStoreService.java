package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.model.Reading;
import dev.datainmotion.airquality.repository.ReadingRepository;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FeatureStoreService {

    private static final Logger log = LoggerFactory.getLogger(FeatureStoreService.class);

    public static final String PM_25 = "PM2.5";
    public static final String PM_10 = "PM10";

    @Autowired
    ReadingRepository readingRepository;

    /**
     * saveObservation
     * @param observation
     * @param msgID
     * @return boolean is saved
     */
    public boolean saveObservation(Observation observation, String msgID) {
        boolean isSaved = false;

        Reading reading = new Reading();
        try {
            if ( observation != null) {
                reading.setReadingID( msgID );
                reading.setLatitude( observation.getLatitude());
                reading.setLongitude( observation.getLongitude());
                reading.setDate_observed(observation.getDateObserved());
                reading.setHour_observed(observation.getHourObserved());
                reading.setLocal_time_zone(observation.getLocalTimeZone());
                reading.setReporting_area(observation.getReportingArea());
                reading.setState_code(observation.getStateCode());

                if ( observation.getParameterName().equalsIgnoreCase(PM_25)) {
                    reading.setAvg_pm25(observation.getAqi());
                    reading.setMax_pm25(observation.getAqi());
                    reading.setMin_pm25(observation.getAqi());
                }
                else if (observation.getParameterName().equalsIgnoreCase(PM_10)) {
                    reading.setAvg_pm10(observation.getAqi());
                    reading.setMax_pm10(observation.getAqi());
                    reading.setMin_pm10(observation.getAqi());
                }
                else {
                    reading.setAvg_ozone(observation.getAqi());
                    reading.setMax_ozone(observation.getAqi());
                    reading.setMin_ozone(observation.getAqi());
                }

                readingRepository.save(reading);
            }
        } catch (Exception e) {
            log.error("Save failed {}",e);
        }

        return isSaved;
    }
}