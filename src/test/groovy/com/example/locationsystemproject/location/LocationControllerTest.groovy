package com.example.locationsystemproject.location

import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@AutoConfigureMockMvc
@SpringBootTest
class LocationControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @InjectMocks
    LocationController locationController

    void setup() {
        mockMvc = standaloneSetup(locationController).build()
    }

    def "when get is performed then the response has status 200 and show page home"() {
        expect: "status 200 and show page home"
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
    }


}
