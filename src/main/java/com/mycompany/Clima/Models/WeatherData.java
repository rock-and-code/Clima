package com.mycompany.Clima.Models;

public class WeatherData {
    private String name;
    private String temperature;
    private String windSpeed;
    private String windDirection;
    private String icon;
    private String shortForecast;
    private String detailedForecast;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
    public String getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getShortForecast() {
        return shortForecast;
    }
    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }
    public String getDetailedForecast() {
        return detailedForecast;
    }
    public void setDetailedForecast(String detailedForecast) {
        this.detailedForecast = detailedForecast;
    }


    public WeatherData name(String name) {
        this.name = name;
        return this;
    }
    public WeatherData temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }
    public WeatherData windSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }
    public WeatherData windDirection(String windDirection) {
        this.windDirection = windDirection;
        return this;
    }
    public WeatherData icon(String icon) {
        this.icon = icon;
        return this;
    }
    public WeatherData shortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
        return this;
    }
    public WeatherData detailedForecast(String detailedForecast) {
        this.detailedForecast = detailedForecast;
        return this;
    }
    @Override
    public String toString() {
        return "WeatherData [name=" + name + ", temperature=" + temperature + ", windSpeed=" + windSpeed
                + ", windDirection=" + windDirection + ", icon=" + icon + ", shortForecast=" + shortForecast
                + ", detailedForecast=" + detailedForecast + "]";
    }
    
    


}
