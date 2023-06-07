package com.mycompany.Clima.Controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mycompany.Clima.Models.CoordinatesFromZipcodeNotFoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handException() {
        return "redirect:/?errorGatheringWeatherForecast";
    }

    @ExceptionHandler(CoordinatesFromZipcodeNotFoundException.class)
    public String handle() {
        return "redirect:/?errorGatheringDataForZipcode";
    }
}
