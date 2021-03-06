package dev.datainmotion.airquality.model;

import java.util.StringJoiner;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;

@Table
public class Reading
{
    @Column(value="readingid")
    private String readingid;

    @Column(value="local_time_zone")
    private String localTimeZone;

    @Column(value="state_code")
    private String stateCode;

    @PrimaryKey(value="reporting_area")
    @Column(value="reporting_area")
    private String reportingArea;

    @Column(value="date_observed")
    private String dateObserved;

    @Column(value="avg_ozone")
    private double avgOzone;

    @Column(value="min_ozone")
    private double minOzone;

    @Column(value="max_ozone")
    private double maxOzone;

    @Column(value="avg_pm10")
    private double avgPm10;

    @Column(value="min_pm10")
    private double minPm10;

    @Column(value="max_pm10")
    private double maxPm10;

    @Column(value="avg_pm25")
    private double avgPm25;

    @Column(value="min_pm25")
    private double minPm25;

    @Column(value="max_pm25")
    private double maxPm25;

    @Column(value="hour_observed")
    private int hourObserved;

    @Column(value="latitude")
    private float latitude;

    @Column(value="longitude")
    private float longitude;

    public Reading() {
        super();
    }

    public Reading(String readingid, String localTimeZone, String stateCode, String reportingArea, String dateObserved, double avgOzone, double minOzone, double maxOzone, double avgPm10, double minPm10, double maxPm10, double avgPm25, double minPm25, double maxPm25, int hourObserved, float latitude, float longitude) {
        super();
        this.readingid = readingid;
        this.localTimeZone = localTimeZone;
        this.stateCode = stateCode;
        this.reportingArea = reportingArea;
        this.dateObserved = dateObserved;
        this.avgOzone = avgOzone;
        this.minOzone = minOzone;
        this.maxOzone = maxOzone;
        this.avgPm10 = avgPm10;
        this.minPm10 = minPm10;
        this.maxPm10 = maxPm10;
        this.avgPm25 = avgPm25;
        this.minPm25 = minPm25;
        this.maxPm25 = maxPm25;
        this.hourObserved = hourObserved;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getReadingid() {
        return readingid;
    }

    public void setReadingid(String readingid) {
        this.readingid = readingid;
    }

    public String getLocalTimeZone() {
        return localTimeZone;
    }

    public void setLocalTimeZone(String localTimeZone) {
        this.localTimeZone = localTimeZone;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getReportingArea() {
        return reportingArea;
    }

    public void setReportingArea(String reportingArea) {
        this.reportingArea = reportingArea;
    }

    public String getDateObserved() {
        return dateObserved;
    }

    public void setDateObserved(String dateObserved) {
        this.dateObserved = dateObserved;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Reading.class.getSimpleName() + "[", "]")
                .add("readingID='" + readingid + "'")
                .add("local_time_zone='" + localTimeZone + "'")
                .add("state_code='" + stateCode + "'")
                .add("reporting_area='" + reportingArea + "'")
                .add("date_observed='" + dateObserved + "'")
                .add("avg_ozone=" + avgOzone)
                .add("min_ozone=" + minOzone)
                .add("max_ozone=" + maxOzone)
                .add("avg_pm10=" + avgPm10)
                .add("min_pm10=" + minPm10)
                .add("max_pm10=" + maxPm10)
                .add("avg_pm25=" + avgPm25)
                .add("min_pm25=" + minPm25)
                .add("max_pm25=" + maxPm25)
                .add("hour_observed=" + hourObserved)
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .toString();
    }

    public double getAvgOzone() {
        return avgOzone;
    }

    public void setAvgOzone(double avgOzone) {
        this.avgOzone = avgOzone;
    }

    public double getMinOzone() {
        return minOzone;
    }

    public void setMinOzone(double minOzone) {
        this.minOzone = minOzone;
    }

    public double getMaxOzone() {
        return maxOzone;
    }

    public void setMaxOzone(double maxOzone) {
        this.maxOzone = maxOzone;
    }

    public double getAvgPm10() {
        return avgPm10;
    }

    public void setAvgPm10(double avgPm10) {
        this.avgPm10 = avgPm10;
    }

    public double getMinPm10() {
        return minPm10;
    }

    public void setMinPm10(double minPm10) {
        this.minPm10 = minPm10;
    }

    public double getMaxPm10() {
        return maxPm10;
    }

    public void setMaxPm10(double maxPm10) {
        this.maxPm10 = maxPm10;
    }

    public double getAvgPm25() {
        return avgPm25;
    }

    public void setAvgPm25(double avgPm25) {
        this.avgPm25 = avgPm25;
    }

    public double getMinPm25() {
        return minPm25;
    }

    public void setMinPm25(double minPm25) {
        this.minPm25 = minPm25;
    }

    public double getMaxPm25() {
        return maxPm25;
    }

    public void setMaxPm25(double maxPm25) {
        this.maxPm25 = maxPm25;
    }

    public int getHourObserved() {
        return hourObserved;
    }

    public void setHourObserved(int hourObserved) {
        this.hourObserved = hourObserved;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}