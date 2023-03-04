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
    def accessLocation = new Location()
    List<Location> accessLocations = new ArrayList<>()

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

        accessLocation.setName("test")
        accessLocation.setAddress("test")

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
        accessLocation.setUser(user)
        testEntityManager.merge(accessLocation)
        accessLocations.add(accessLocation)
        user2.setReadOnlyLocations(accessLocations)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyReadOnlyLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == accessLocation
        }
    }

    def "should find all locations with admin access that user has"() {
        given: "set the data for method testing"
        user.setId(3L)
        testEntityManager.merge(user)
        accessLocation.setUser(user)
        testEntityManager.merge(accessLocation)
        accessLocations.add(accessLocation)
        user2.setAdminLocations(accessLocations)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyAdminLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == accessLocation
        }
    }
}
