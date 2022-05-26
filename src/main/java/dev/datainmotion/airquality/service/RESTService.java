package dev.datainmotion.airquality.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.StringJoiner;

@Configuration
public class RESTService {

    private static final String DEFAULT_ZIPCODE = "08520";

    @Value("${zipcodes}")
    String[] arrayOfStrings;

    @Value("${airnowapi.url:http://localhost:8080}")
    String airnowapi;

    @Value("${airnowapi.uri}")
    String airnowapiuri;

    @Value("${airnowapi.api.key}")
    String apikey;

    @Value("${airnowapi.key.name}")
    String airnowapikeyname;

    /**
     *
     * @return
     */
    public String getURI() {
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
                .add(airnowapiuri)
                .add(zipCode)
                .add(airnowapikeyname)
                .add(apikey)
                .toString();
    }
}