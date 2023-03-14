package dev.datainmotion.airquality.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "DateObserved",
    "HourObserved",
    "LocalTimeZone",
    "ReportingArea",
    "StateCode",
    "Latitude",
    "Longitude",
    "ParameterName",
    "AQI",
    "Category"
})
public class Observation {

    @JsonProperty("DateObserved")
    private String dateObserved;
    @JsonProperty("HourObserved")
    private Integer hourObserved;
    @JsonProperty("LocalTimeZone")
    private String localTimeZone;
    @JsonProperty("ReportingArea")
    private String reportingArea;
    @JsonProperty("StateCode")
    private String stateCode;
    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("Longitude")
    private Double longitude;
    @JsonProperty("ParameterName")
    private String parameterName;
    @JsonProperty("AQI")
    private Integer aqi;
    @JsonProperty("UUID")
    private String uuid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Observation() {
        super();
    }


    public Observation(String dateObserved, Integer hourObserved, String localTimeZone, String reportingArea, String stateCode, Double latitude, Double longitude, String parameterName, Integer aqi, String uuid) {
        super();
        this.dateObserved = dateObserved;
        this.hourObserved = hourObserved;
        this.localTimeZone = localTimeZone;
        this.reportingArea = reportingArea;
        this.stateCode = stateCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parameterName = parameterName;
        this.aqi = aqi;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("DateObserved")
    public String getDateObserved() {
        return dateObserved;
    }

    @JsonProperty("DateObserved")
    public void setDateObserved(String dateObserved) {
        this.dateObserved = dateObserved;
    }

    @JsonProperty("HourObserved")
    public Integer getHourObserved() {
        return hourObserved;
    }

    @JsonProperty("HourObserved")
    public void setHourObserved(Integer hourObserved) {
        this.hourObserved = hourObserved;
    }

    @JsonProperty("LocalTimeZone")
    public String getLocalTimeZone() {
        return localTimeZone;
    }

    @JsonProperty("LocalTimeZone")
    public void setLocalTimeZone(String localTimeZone) {
        this.localTimeZone = localTimeZone;
    }

    @JsonProperty("ReportingArea")
    public String getReportingArea() {
        return reportingArea;
    }

    @JsonProperty("ReportingArea")
    public void setReportingArea(String reportingArea) {
        this.reportingArea = reportingArea;
    }

    @JsonProperty("StateCode")
    public String getStateCode() {
        return stateCode;
    }

    @JsonProperty("StateCode")
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @JsonProperty("Latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("Latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("Longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("Longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("ParameterName")
    public String getParameterName() {
        return parameterName;
    }

    @JsonProperty("ParameterName")
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @JsonProperty("AQI")
    public Integer getAqi() {
        return aqi;
    }

    @JsonProperty("AQI")
    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "dateObserved='" + dateObserved + '\'' +
                ", hourObserved=" + hourObserved +
                ", localTimeZone='" + localTimeZone + '\'' +
                ", reportingArea='" + reportingArea + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", parameterName='" + parameterName + '\'' +
                ", aqi=" + aqi +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}