package com.example.locationsystemproject.location

import com.example.locationsystemproject.user.User
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@DataJpaTest
class LocationRepositoryTest extends Specification {

    @Autowired
    TestEntityManager testEntityManager

    @Autowired
    LocationRepository locationRepository

    def location = new Location()
    def user = new User()

    def user2 = new User()
    def readOnlyLocation = new Location()
    List<Location> readOnlyLocations = new ArrayList<>()

    def adminLocation = new Location()
    List<Location> adminLocations = new ArrayList<>()

    void setup() {
        user.setUsername("test")
        user.setPassword("test")
        user.setName("test")
        user.setEnabled(1)

        user2.setName("test")
        user2.setEnabled(1)
        user2.setPassword("test")
        user2.setUsername("grgrgrg@gmail.com")
        testEntityManager.merge(user2)

        location.setName("test")
        location.setAddress("test")

        readOnlyLocation.setName("test")
        readOnlyLocation.setAddress("test")

        adminLocation.setName("test")
        adminLocation.setAddress("test")
    }


    def "should find all locations that user added"() {
        given: "set the data for method testing"
        user.setId(1L)
        testEntityManager.merge(user)
        location.setUser(user)
        testEntityManager.merge(location)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyLocations(location.user.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc.user.getId() == user.getId()
        }
    }

    def "should find all locations with read only access that user has"() {
        given: "set the data for method testing"
        user.setId(2L)
        testEntityManager.merge(user)
        readOnlyLocation.setUser(user)
        testEntityManager.merge(readOnlyLocation)
        readOnlyLocations.add(readOnlyLocation)
        user2.setReadOnlyLocations(readOnlyLocations)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyReadOnlyLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == readOnlyLocation
        }
    }

    def "should find all locations with admin access that user has"() {
        given: "set the data for method testing"
        user.setId(3L)
        testEntityManager.merge(user)
        adminLocation.setUser(user)
        testEntityManager.merge(adminLocation)
        adminLocations.add(adminLocation)
        user2.setAdminLocations(adminLocations)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyAdminLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == adminLocation
        }
    }
}
