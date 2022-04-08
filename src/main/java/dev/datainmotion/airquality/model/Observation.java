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
    @JsonProperty("Category")
    private Category category;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Observation() {
        super();
    }

    /**
     * 
     * @param dateObserved
     * @param latitude
     * @param localTimeZone
     * @param aqi
     * @param stateCode
     * @param parameterName
     * @param hourObserved
     * @param category
     * @param reportingArea
     * @param longitude
     */
    public Observation(String dateObserved, Integer hourObserved, String localTimeZone, String reportingArea, String stateCode, Double latitude, Double longitude, String parameterName, Integer aqi, Category category) {
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
        this.category = category;
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

    @JsonProperty("Category")
    public Category getCategory() {
        return category;
    }

    @JsonProperty("Category")
    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Observation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dateObserved");
        sb.append('=');
        sb.append(((this.dateObserved == null)?"<null>":this.dateObserved));
        sb.append(',');
        sb.append("hourObserved");
        sb.append('=');
        sb.append(((this.hourObserved == null)?"<null>":this.hourObserved));
        sb.append(',');
        sb.append("localTimeZone");
        sb.append('=');
        sb.append(((this.localTimeZone == null)?"<null>":this.localTimeZone));
        sb.append(',');
        sb.append("reportingArea");
        sb.append('=');
        sb.append(((this.reportingArea == null)?"<null>":this.reportingArea));
        sb.append(',');
        sb.append("stateCode");
        sb.append('=');
        sb.append(((this.stateCode == null)?"<null>":this.stateCode));
        sb.append(',');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("parameterName");
        sb.append('=');
        sb.append(((this.parameterName == null)?"<null>":this.parameterName));
        sb.append(',');
        sb.append("aqi");
        sb.append('=');
        sb.append(((this.aqi == null)?"<null>":this.aqi));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}