package com.project.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MainController {
    @Autowired
    private WeatherRepo weatherRepo;

    private RestTemplate restTemplate;

    private final String apikey = "fd7b84a077191a9d5c6744c837838240";

    private ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/weatherall")
    public String showWeatherList(Model model) {
        model.addAttribute("weathers", weatherRepo.findAll());
        return "weatherall";
    }

    @GetMapping("/weather")
    public String addCity(Model model) {
        model.addAttribute("city", new City());
        return "weather";
    }

    @PostMapping("/weather")
    public String getTemperature(@ModelAttribute City city) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate
                .getForObject("https://api.openweathermap.org/data/2.5/weather?q=" + city.getCity_name() +
                        "&appid=" + apikey + "&units=metric", String.class);

        JsonNode productNode = new ObjectMapper().readTree(response);
        Weather weather = new Weather();

        weather.setWeatherCondition(productNode.get("weather").get(0).get("main").asText());
        weather.setTemperature(productNode.get("main").get("temp").asInt());
        weather.setCity(productNode.get("name").asText());
        weather.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        weatherRepo.save(weather);

        return "redirect:/weatherall";
    }

    @GetMapping("/delete_weather/{id}")
    public String deleteWeather(@PathVariable("id") Integer id, Model model) {
        Weather weather = weatherRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid weather Id:" + id));
        weatherRepo.delete(weather);
        return "redirect:/weatherall";
    }


    @RequestMapping("/getIP")
    @ResponseBody
    public String implicitTransfer(HttpServletRequest request) {
        String browserName = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        return "Browser name: " + browserName + System.lineSeparator() + "IP address: " + ipAddress;
    }

}