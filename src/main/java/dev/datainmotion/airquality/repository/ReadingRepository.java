package dev.datainmotion.airquality.repository;

import dev.datainmotion.airquality.model.Reading;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Repository
public interface ReadingRepository extends CassandraRepository<Reading, String> {

    // primary key
    Optional<Reading> findByReportingArea(String reportingArea);

    @Query(allowFiltering = true)
    Optional<Reading> findByStateCode(String state_code);

    @Query(allowFiltering = true)
    Optional<Reading> findByMaxPm10(String max_pm10);

    @Query(allowFiltering = true)
    Optional<Reading> findByMaxOzone(String max_ozone);

    @Query(allowFiltering = true)
    Optional<Reading> findByMaxPm25(String max_pm25);
}