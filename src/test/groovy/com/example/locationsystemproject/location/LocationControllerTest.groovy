package com.example.locationsystemproject.location

import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class LocationControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    LocationController locationController;

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build()
    }

    def "when get is performed then the response has status 200 and show page home"() {
        expect: "status 200 and show page home"
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"))
    }




}
