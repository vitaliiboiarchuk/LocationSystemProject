package com.example.locationsystemproject.location

import com.example.locationsystemproject.user.User
import com.example.locationsystemproject.user.UserRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class LocationControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @InjectMocks
    LocationController locationController

    @Mock
    LocationRepository locationRepository

    @Mock
    UserRepository userRepository

    def location = new Location()

    def user = new User()

    List<User> accessList = new ArrayList<>()

    def readOnlyLocation = new Location()

    List<Location> readOnlyLocations = new ArrayList<>()

    def adminLocation = new Location()

    List<Location> adminLocations = new ArrayList<>()

    void setup() {
        mockMvc = standaloneSetup(locationController).build()

        //should show friends of user
        location.setId(1)
        Mockito.when(locationRepository.getReferenceById(1)).thenReturn(location)
        accessList = Arrays.asList(user)
        Mockito.when(userRepository.findAllReadOnlyFriendsOnLocation(location)).thenReturn(accessList)
        Mockito.when(userRepository.findAllAdminFriendsOnLocation(location)).thenReturn(accessList)

//      could change access of location that was sharing with friend who has read only / admin access
        user.setId(1L)
        Mockito.when(userRepository.getReferenceById(1L)).thenReturn(user)
        readOnlyLocation.setId(2)
        Mockito.when(locationRepository.getReferenceById(2)).thenReturn(readOnlyLocation)
        readOnlyLocations.add(readOnlyLocation)
        user.setReadOnlyLocations(readOnlyLocations)
        adminLocation.setId(3)
        Mockito.when(locationRepository.getReferenceById(3)).thenReturn(adminLocation)
        adminLocations.add(adminLocation)
        user.setAdminLocations(adminLocations)
    }

    def "when get is performed then the response has status 200 and show page home"() {
        expect: "status 200 and show page home"
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
    }

    @WithMockUser
    def "should show friends of user"() {
        expect: "status 200 and show user friends"
        mockMvc.perform(get("/showFriends/{id}/", 1))
                .andExpect(model().attributeExists("location"))
                .andExpect(model().attributeExists("readOnlyUsers"))
                .andExpect(model().attributeExists("adminUsers"))
                .andExpect(view().name("locOperations/showFriends"))
                .andExpect(status().isOk())
    }

    @WithMockUser
    def "could change access of location that was sharing with friend who has read only access"() {
        expect: "status 200 and change access of location that was sharing with friend who has read only access"
        mockMvc.perform(get("/changeAccess/{locationId}/{userId}/", 2, 1L))
                .andExpect(model().attributeExists("location"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("showAdminLocations"))
                .andExpect(view().name("locOperations/changeAccess"))
                .andExpect(status().isOk())
    }

    @WithMockUser
    def "could change access of location that was sharing with friend who has admin access"() {
        expect: "status 200 and change access of location that was sharing with friend who has admin access"
        mockMvc.perform(get("/changeAccess/{locationId}/{userId}/", 3, 1L))
                .andExpect(model().attributeExists("location"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("showReadOnlyLocations"))
                .andExpect(view().name("locOperations/changeAccess"))
                .andExpect(status().isOk())
    }

    @WithMockUser
    def "update user after change access"() {
        expect: "status 200 and update user after change access"
        mockMvc.perform(post("/changeAccess"))
                .andExpect(status().is3xxRedirection())
    }

    @WithMockUser
    def "give user a read only access after share location with him"() {
        expect: "status 200 and give user a read only access after share location with him"
        mockMvc.perform(post("/shareReadOnly"))
                .andExpect(status().is3xxRedirection())
    }

    @WithMockUser
    def "give user an admin access after share location with him"() {
        expect: "status 200 and give user an admin access after share location with him"
        mockMvc.perform(post("/shareAdmin"))
                .andExpect(status().is3xxRedirection())
    }


}
