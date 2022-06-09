package dev.datainmotion.airquality.service;

import dev.datainmotion.airquality.model.Observation;
import dev.datainmotion.airquality.model.Reading;
import dev.datainmotion.airquality.repository.ReadingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureStoreService {

    private static final Logger log = LoggerFactory.getLogger(FeatureStoreService.class);

    public static final String PM_25 = "PM2.5";
    public static final String PM_10 = "PM10";

    @Autowired
    ReadingRepository readingRepository;

    /**
     *
     * @param observation
     * @param reading
     * @return
     */
    public boolean updateIfMax(Observation observation, Reading reading) {
        boolean isSaved = false;

        try {
            if ( observation != null && reading != null) {
                if ( observation.getParameterName().equalsIgnoreCase(PM_25)) {

                    if ( reading.getMaxPm25() < observation.getAqi()) {
                        reading.setMaxPm25(observation.getAqi());
                    }
                    if ( reading.getMinPm25() > observation.getAqi()) {
                        reading.setMinPm25(observation.getAqi());
                    }
                }
                else if (observation.getParameterName().equalsIgnoreCase(PM_10)) {
                    if ( reading.getMaxPm10() < observation.getAqi()) {
                        reading.setMaxPm10(observation.getAqi());
                    }
                    if ( reading.getMinPm10() > observation.getAqi()) {
                        reading.setMinPm10(observation.getAqi());
                    }
                }
                else {
                    if ( reading.getMaxOzone() < observation.getAqi()) {
                        reading.setMaxOzone(observation.getAqi());
                    }
                    if ( reading.getMinOzone() > observation.getAqi()) {
                        reading.setMinOzone(observation.getAqi());
                    }
                }

                readingRepository.save(reading);
                isSaved = true;
            }
        } catch (Throwable e) {
            log.error("Save failed {}",e);
            isSaved = false;
        }

        return isSaved;
    }

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
                reading.setReadingid( msgID );
                try {
                    reading.setLatitude( observation.getLatitude().floatValue());
                    reading.setLongitude( observation.getLongitude().floatValue());
                } catch (Throwable e) {
                    log.error("lat/long cast error failed {}",e);
                }
                reading.setDateObserved(observation.getDateObserved());
                reading.setHourObserved(observation.getHourObserved());
                reading.setLocalTimeZone(observation.getLocalTimeZone());
                reading.setReportingArea(observation.getReportingArea());
                reading.setStateCode(observation.getStateCode());

                if ( observation.getParameterName().equalsIgnoreCase(PM_25)) {
                    reading.setAvgPm25(observation.getAqi());
                    reading.setMaxPm25(observation.getAqi());
                    reading.setMinPm25(observation.getAqi());
                }
                else if (observation.getParameterName().equalsIgnoreCase(PM_10)) {
                    reading.setAvgPm10(observation.getAqi());
                    reading.setMaxPm10(observation.getAqi());
                    reading.setMinPm10(observation.getAqi());
                }
                else {
                    reading.setAvgOzone(observation.getAqi());
                    reading.setMaxOzone(observation.getAqi());
                    reading.setMinOzone(observation.getAqi());
                }

                readingRepository.save(reading);

                isSaved = true;
            }
        } catch (Exception e) {
            log.error("Save failed {}",e);
            isSaved = false;
        }

        return isSaved;
    }
}