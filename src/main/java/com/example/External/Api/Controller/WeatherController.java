package com.example.External.Api.Controller;

import com.example.External.Api.Service.WeatherService;
import com.example.External.Api.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city) {


        WeatherResponse weatherResponse = weatherService.getWeather(city);
        if (weatherResponse == null) {
            return new ResponseEntity<>("Weather data not found.", HttpStatus.NOT_FOUND);
        }

        // Prepare data for frontend
        Map<String, Object> responseData = new HashMap<>();

        // Request Info
        responseData.put("requestType", weatherResponse.getRequest().getType());
        responseData.put("query", weatherResponse.getRequest().getQuery());
        responseData.put("unit", weatherResponse.getRequest().getUnit());

        // Location Info
        responseData.put("locationName", weatherResponse.getLocation().getName());
        responseData.put("country", weatherResponse.getLocation().getCountry());
        responseData.put("region", weatherResponse.getLocation().getRegion());
        responseData.put("timezone", weatherResponse.getLocation().getTimezoneId());
        responseData.put("localtime", weatherResponse.getLocation().getLocaltime());

        // Current Weather Info
        WeatherResponse.Current current = weatherResponse.getCurrent();
        responseData.put("temperature", current.getTemperature());
        responseData.put("feelsLike", current.getFeelslike());
        responseData.put("weatherDescriptions", current.getWeatherDescriptions());
        responseData.put("weatherIcons", current.getWeatherIcons());
        responseData.put("windSpeed", current.getWindSpeed());
        responseData.put("windDirection", current.getWindDir());
        responseData.put("humidity", current.getHumidity());
        responseData.put("pressure", current.getPressure());
        responseData.put("visibility", current.getVisibility());
        responseData.put("uvIndex", current.getUvIndex());
        responseData.put("cloudCover", current.getCloudcover());
        responseData.put("precipitation", current.getPrecip());
        System.out.println(responseData.get("temperature"));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
