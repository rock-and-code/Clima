package com.mycompany.Clima.Controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.Clima.Models.Coordinates;
import com.mycompany.Clima.Models.CoordinatesDto;
import com.mycompany.Clima.Models.CoordinatesFromZipcodeNotFoundException;
import com.mycompany.Clima.Models.WeatherData;
import com.mycompany.Clima.Models.ZipcodeDto;

/**
 * Controller to handle weather-related requests
 */
@Controller
public class WeatherController {

    /**
     * GET mapping for the weather form page
     * 
     * @param model the Model object to add attributes to
     * @return the name of the form view
     */
    @GetMapping("/")
    public String getForm(Model model) {
        // This method renders the weather form view.
        CoordinatesDto coordinatesDto = new CoordinatesDto();
        ZipcodeDto zipcodeDto = new ZipcodeDto();
        
        // Adds the CoordinatesDto object to the model.
        model.addAttribute("coordinatesDto", coordinatesDto);

        // Adds the ZipcodeDto object to the model.
        model.addAttribute("zipcodeDto", zipcodeDto);
        
        // Returns the name of the form view.
        return "form";
    }

    /**
     * POST mapping for getting the weather data
     * 
     * @param zipcodeDto     the DTO for the zipcode to get weather data for
     * @param coordinatesDto the DTO for the coordinates to get weather data for
     * @param model          the Model object to add attributes to
     * @return the name of the weather details view
     * @throws Exception
     */
    @PostMapping("/")
    public String getWeather(@ModelAttribute ZipcodeDto zipcodeDto,
            @ModelAttribute CoordinatesDto coordinatesDto, Model model) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        List<WeatherData> weatherData = null;
        // If the user has provided a zipcode, get the coordinates from it.
        if (coordinatesDto.getLatitude() == null) {

            // Gets the coordinates from the zipcode using an API.
            Coordinates coordinates = null;
            try {
               coordinates =  getCoordinatesFromZipcode(zipcodeDto.getZipcode(), restTemplate);
            } catch (Exception e) {
                throw new CoordinatesFromZipcodeNotFoundException();
            }
        
            
            if (coordinates == null) {
                return "redirect:/errorGatheringDataForZipcode";
            }

            // Sets the latitude and longitude of the CoordinatesDto object to the
            // coordinates returned from the API.
            coordinatesDto.setLatitude(coordinates.getLat());
            coordinatesDto.setLongitude(coordinates.getLon());
        }

        // Gets the weather data for the given coordinates or zipcode.
        weatherData = getWeatherData(coordinatesDto, restTemplate);
        // Redirects user to the form and display an error alert if there is any problem
        // from the api call
        if (weatherData == null) {
            return "redirect:/?errorGatheringDataForCoordinates";
        }

        // Adds the weather data to the model.
        model.addAttribute("weatherData", weatherData);

        // Returns the name of the weather details view.
        return "weatherForecast";

    }

    // This method gets the coordinates from a zipcode using the OpenWeather API.
    private Coordinates getCoordinatesFromZipcode(String zipcode, RestTemplate restTemplate) throws Exception {
        // The API key for the OpenWeather API.
        String API_KEY = "YOUR_KEY"; //ENTER YOUR OPENWEATHER API KEY HERE

        // Constructs the URL for the OpenWeather API.
        String geoCoderURI = String.format("http://api.openweathermap.org/geo/1.0/zip?appid=%s&zip=%s,US", API_KEY, zipcode);

        // Makes the API call and gets the coordinates.
        Coordinates coordinates = restTemplate.getForObject(geoCoderURI, Coordinates.class);

        // Returns the coordinates.
        return coordinates;
    }

    // This method gets the weather forecast using the National Weather Service API.
    private List<WeatherData> getWeatherData(CoordinatesDto coordinatesDto, RestTemplate restTemplate) throws Exception {

        // Constructs the URL for the National Weather Service API.
        String forecastGridByCoordinatesURI = String.format("https://api.weather.gov/points/%s,%s", coordinatesDto.getLatitude(), 
                coordinatesDto.getLongitude());

        // Makes the API call and gets the JSON response.
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(forecastGridByCoordinatesURI, JsonNode.class);

         // Get the URL from the National Weather Service API for the weather forecast.
        String weatherURI = response.getBody().get("properties").get("forecast").asText();

        // Makes the API call and gets the Weather forecast.
        ResponseEntity<JsonNode> forecastData = restTemplate.getForEntity(weatherURI, JsonNode.class);

        if(forecastData.getStatusCode() != HttpStatusCode.valueOf(200)) {
            throw new Exception();
        }

        // Gets the `periods` node from the JSON response.
        Iterator<JsonNode> periods = forecastData.getBody().get("properties").get("periods").elements();

        // Create a list to store the weather data.
        List<WeatherData> weatherData = new LinkedList<>();

        // Iterate over the `periods` node and create a `WeatherData` object for each period.
        while (periods.hasNext()) {
            JsonNode jsonNode = periods.next();
            var data = new WeatherData()
                    .name(jsonNode.get("name").asText())
                    .temperature(
                            jsonNode.get("temperature").asText() + " " + jsonNode.get("temperatureUnit").asText())
                    .windSpeed(jsonNode.get("windSpeed").asText())
                    .windDirection(jsonNode.get("windDirection").asText())
                    .icon(jsonNode.get("icon").asText())
                    .shortForecast(jsonNode.get("shortForecast").asText())
                    .detailedForecast(jsonNode.get("detailedForecast").asText());
            weatherData.add(data);
        }

        // Return the list of weather data.
        return weatherData;
    }
}
