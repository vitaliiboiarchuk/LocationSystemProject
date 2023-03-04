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

    def accessLocation = new Location()

    List<Location> accessLocations = new ArrayList<>()

    def user2 = new User()

    void setup() {
        user.setId(1L)
        user.setUsername("test@gmail.com")
        user.setPassword("test")
        user.setEnabled(1)
        testEntityManager.merge(user)

        location.setName("test")
        location.setAddress("test")
        location.setUser(user)
        testEntityManager.merge(location)

        accessLocation.setName("accessTest")
        accessLocation.setAddress("accessTest")
        accessLocation.setUser(user)
        testEntityManager.persistAndFlush(accessLocation)

        accessLocations.add(accessLocation)

        user2.setId(2L)
        user2.setUsername("test2")
        user2.setPassword("test")
        user2.setEnabled(1)
    }

    def "should find all locations that user added"() {
        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyLocations(location.user.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc.user.getId() == user.getId()
        }
    }

    def "should find all locations with read only access that user has"() {
        given: "set the data for method testing"
        user2.setReadOnlyLocations(accessLocations)
        testEntityManager.merge(user2)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyReadOnlyLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == accessLocation
        }
    }

    def "should find all locations with admin access that user has"() {
        given: "set the data for method testing"
        user2.setAdminLocations(accessLocations)
        testEntityManager.merge(user2)

        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyAdminLocations(user2.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc == accessLocation
        }
    }
}
