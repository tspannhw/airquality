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
    Optional<Reading> findByState_Code(String state_code);
    Optional<Reading> findByMax_Pm10(String max_pm10);
    Optional<Reading> findByMax_Ozone(String max_ozone);
    Optional<Reading> findByMax_Pm25(String max_pm25);
//    List<Reading> findAll();
}
