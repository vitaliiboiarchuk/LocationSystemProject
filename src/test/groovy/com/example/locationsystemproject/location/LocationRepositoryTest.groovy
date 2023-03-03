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


    void setup() {
        user.setUsername("test")
        user.setId(1L)
        user.setPassword("test")
        user.setName("test")
        user.setEnabled(1)
        testEntityManager.merge(user)
        location.setName("test")
        location.setAddress("test")
        location.setUser(user)
        testEntityManager.merge(location)
    }

    def "should find all locations that user added"() {
        when: "calling the testing method"
        List<Location> result = locationRepository.findAllMyLocations(location.user.getId())

        then: "comparing expected with actual"
        for (Location loc : result) {
            loc.user.getId() == user.getId()
        }
    }
}
