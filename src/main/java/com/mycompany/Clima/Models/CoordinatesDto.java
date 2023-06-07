package com.mycompany.Clima.Models;

public class CoordinatesDto {
    Double latitude;
    Double longitude;
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public CoordinatesDto latittude(double latittude) {
        this.latitude = latittude;
        return this;
    }
    public CoordinatesDto longitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
