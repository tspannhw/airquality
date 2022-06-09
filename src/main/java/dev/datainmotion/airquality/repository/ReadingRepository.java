package dev.datainmotion.airquality.repository;

import dev.datainmotion.airquality.model.Reading;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ReadingRepository extends CrudRepository<Reading, String> {
    Optional<Reading> findByReadingID(String readingID);
    Optional<Reading> findByStateCode(String state_code);
    Optional<Reading> findBymaxpm10(String max_pm10);
    Optional<Reading> findBymaxozone(String max_ozone);
    Optional<Reading> findBymaxpm25(String max_pm25);
}