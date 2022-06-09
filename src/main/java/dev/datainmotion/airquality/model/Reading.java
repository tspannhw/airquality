package dev.datainmotion.airquality.model;

import java.util.StringJoiner;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Reading
{
    @PrimaryKey
    private String readingID;

    private String local_time_zone;
    private String state_code;
    private String reporting_area;
    private String date_observed;
    private double avg_ozone;
    private double min_ozone;
    private double max_ozone;
    private double avg_pm10;
    private double min_pm10;
    private double max_pm10;
    private double avg_pm25;
    private double min_pm25;
    private double max_pm25;
    private int hour_observed;
    private double latitude;
    private double longitude;

    /**
     *
     * @param readingID
     * @param local_time_zone
     * @param state_code
     * @param reporting_area
     * @param date_observed
     * @param avg_ozone
     * @param min_ozone
     * @param max_ozone
     * @param avg_pm10
     * @param min_pm10
     * @param max_pm10
     * @param avg_pm25
     * @param min_pm25
     * @param max_pm25
     * @param hour_observed
     * @param latitude
     * @param longitude
     */
    public Reading(String readingID, String local_time_zone, String state_code, String reporting_area, String date_observed, double avg_ozone, double min_ozone, double max_ozone, double avg_pm10, double min_pm10, double max_pm10, double avg_pm25, double min_pm25, double max_pm25, int hour_observed, double latitude, double longitude) {
        super();
        this.readingID = readingID;
        this.local_time_zone = local_time_zone;
        this.state_code = state_code;
        this.reporting_area = reporting_area;
        this.date_observed = date_observed;
        this.avg_ozone = avg_ozone;
        this.min_ozone = min_ozone;
        this.max_ozone = max_ozone;
        this.avg_pm10 = avg_pm10;
        this.min_pm10 = min_pm10;
        this.max_pm10 = max_pm10;
        this.avg_pm25 = avg_pm25;
        this.min_pm25 = min_pm25;
        this.max_pm25 = max_pm25;
        this.hour_observed = hour_observed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Reading() {
        super();
    }

    public String getReadingID() {
        return readingID;
    }

    public void setReadingID(String readingID) {
        this.readingID = readingID;
    }

    public String getLocal_time_zone() {
        return local_time_zone;
    }

    public void setLocal_time_zone(String local_time_zone) {
        this.local_time_zone = local_time_zone;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getReporting_area() {
        return reporting_area;
    }

    public void setReporting_area(String reporting_area) {
        this.reporting_area = reporting_area;
    }

    public String getDate_observed() {
        return date_observed;
    }

    public void setDate_observed(String date_observed) {
        this.date_observed = date_observed;
    }

    public Reading(double avg_ozone, double min_ozone, double max_ozone, double avg_pm10, double min_pm10, double max_pm10, double avg_pm25, double min_pm25, double max_pm25, int hour_observed, double latitude, double longitude) {
        super();
        this.avg_ozone = avg_ozone;
        this.min_ozone = min_ozone;
        this.max_ozone = max_ozone;
        this.avg_pm10 = avg_pm10;
        this.min_pm10 = min_pm10;
        this.max_pm10 = max_pm10;
        this.avg_pm25 = avg_pm25;
        this.min_pm25 = min_pm25;
        this.max_pm25 = max_pm25;
        this.hour_observed = hour_observed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Reading.class.getSimpleName() + "[", "]")
                .add("readingID='" + readingID + "'")
                .add("local_time_zone='" + local_time_zone + "'")
                .add("state_code='" + state_code + "'")
                .add("reporting_area='" + reporting_area + "'")
                .add("date_observed='" + date_observed + "'")
                .add("avg_ozone=" + avg_ozone)
                .add("min_ozone=" + min_ozone)
                .add("max_ozone=" + max_ozone)
                .add("avg_pm10=" + avg_pm10)
                .add("min_pm10=" + min_pm10)
                .add("max_pm10=" + max_pm10)
                .add("avg_pm25=" + avg_pm25)
                .add("min_pm25=" + min_pm25)
                .add("max_pm25=" + max_pm25)
                .add("hour_observed=" + hour_observed)
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .toString();
    }

    public double getAvg_ozone() {
        return avg_ozone;
    }

    public void setAvg_ozone(double avg_ozone) {
        this.avg_ozone = avg_ozone;
    }

    public double getMin_ozone() {
        return min_ozone;
    }

    public void setMin_ozone(double min_ozone) {
        this.min_ozone = min_ozone;
    }

    public double getMax_ozone() {
        return max_ozone;
    }

    public void setMax_ozone(double max_ozone) {
        this.max_ozone = max_ozone;
    }

    public double getAvg_pm10() {
        return avg_pm10;
    }

    public void setAvg_pm10(double avg_pm10) {
        this.avg_pm10 = avg_pm10;
    }

    public double getMin_pm10() {
        return min_pm10;
    }

    public void setMin_pm10(double min_pm10) {
        this.min_pm10 = min_pm10;
    }

    public double getMax_pm10() {
        return max_pm10;
    }

    public void setMax_pm10(double max_pm10) {
        this.max_pm10 = max_pm10;
    }

    public double getAvg_pm25() {
        return avg_pm25;
    }

    public void setAvg_pm25(double avg_pm25) {
        this.avg_pm25 = avg_pm25;
    }

    public double getMin_pm25() {
        return min_pm25;
    }

    public void setMin_pm25(double min_pm25) {
        this.min_pm25 = min_pm25;
    }

    public double getMax_pm25() {
        return max_pm25;
    }

    public void setMax_pm25(double max_pm25) {
        this.max_pm25 = max_pm25;
    }

    public int getHour_observed() {
        return hour_observed;
    }

    public void setHour_observed(int hour_observed) {
        this.hour_observed = hour_observed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}