package com.example.locationsystemproject.user

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @InjectMocks
    UserController userController

    void setup() {
        mockMvc = standaloneSetup(userController).build()

    }

    def "when get is performed then status is 200 and show the registration form"() {
        expect: "status is 200 and show the registration form"
        mockMvc.perform(get("/registration"))
                .andExpect(view().name("entrance/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
    }

    def "when get is performed the status is 200 and show the login form"() {
        expect: "status is 200 and show the login form"
        mockMvc.perform(get("/login"))
                .andExpect(view().name("entrance/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
    }
}
