package com.project.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void showWeatherList() throws Exception {
                mockMvc.perform(get("/weatherall"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void addCity() throws Exception {
        mockMvc.perform(get("/weather"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void getTemperature() throws Exception {
        mockMvc.perform(get("/weather"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void deleteWeather() throws Exception {
        mockMvc.perform(get("/delete_weather/36"))
                .andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    void implicitTransfer() throws Exception {
        mockMvc.perform(get("/getIP"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}