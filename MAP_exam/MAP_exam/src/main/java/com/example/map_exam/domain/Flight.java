package com.example.map_exam.domain;

import java.time.LocalDateTime;

public class Flight extends Entity<Long>{
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private LocalDateTime landingTime;
    private Integer seats;

    public Flight(String from, String to, LocalDateTime departureTime, LocalDateTime landingTime, Integer seats) {
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setFrom(String from) {
        this.from  = from;
    }

    public void setTo(String to) {
        this.to  = to;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime  = departureTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime  = landingTime;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return from + " " + to + " " + departureTime.toString() + " " + landingTime.toString();
    }
}
