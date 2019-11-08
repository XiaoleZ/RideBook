package com.example.xiaole2_ridebook;
import java.io.Serializable;

/**
 * This Ride class contains all the attributes of a ride.
 * It has get and set its attributes including
 * date, time, distance, average speed, average cadence, and comment
 */
public class Ride implements Serializable {

    private static final long serialVersionUID = 1L;

    private String date;
    private String time;
    private  String distance;
    private String avg_speed;
    private  String avg_cadence;
    private String comment;

    public Ride(String date, String time, String distance, String avg_speed, String avg_cadence, String comment) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.avg_speed = avg_speed;
        this.avg_cadence = avg_cadence;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(String avg_speed) {
        this.avg_speed = avg_speed;
    }

    public String getAvg_cadence() {
        return avg_cadence;
    }

    public void setAvg_cadence(String avg_cadence) {
        this.avg_cadence = avg_cadence;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
