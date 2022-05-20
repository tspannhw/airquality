package dev.datainmotion.airquality.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.datainmotion.airquality.model.Category;
import dev.datainmotion.airquality.model.Observation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Random;
import java.util.StringJoiner;

/**
 *
 */
@Configuration
public class DataUtility {
    private static final String DEFAULT_ZIPCODE = "08520";

    @Value("${zipcodes}")
    String[] arrayOfStrings;

    @Value("${airnowapi.url:http://localhost:8080}")
    String airnowapi;

    /**
     *
     * @return
     */
    public String getURL() {
        Random rand = new Random();
        String zipCode = null;

        try {
            zipCode= arrayOfStrings[rand.nextInt(arrayOfStrings.length)];
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ( zipCode == null || zipCode.trim().length() <= 0) {
            zipCode = arrayOfStrings[0];
        }

        if ( zipCode == null || zipCode.trim().length() <= 0) {
            zipCode = DEFAULT_ZIPCODE;
        }

        return new StringJoiner("", "", "")
                .add(airnowapi)
                .add(zipCode)
                .toString();
    }

    /**
     * build json output
     * @param  observation   observation as an object
     * @return String device as JSON String
     */
    public static String serializeToJSON(Observation observation) {
        String jsonValue = "";
        try {
            if (observation != null) {
                ObjectMapper mapper = new ObjectMapper();
                jsonValue = mapper.writeValueAsString(observation);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return jsonValue;
    }

    /**
     * device to json
     * @param observation   observation
     * @return byte[] json String
     */
    public static byte[] serialize(Observation observation) {
        if ( observation == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = "{}";
        try {
            json = mapper.writeValueAsString(observation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if ( json == null) {
            json = "{}";
        }

        return String.format(Locale.US, "%s", json).getBytes();
    }
}