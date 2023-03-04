package com.example.locationsystemproject.user

import com.example.locationsystemproject.location.Location
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest extends Specification {

    @Autowired
    TestEntityManager testEntityManager

    @Autowired
    UserRepository userRepository

    def user = new User()

    def user2 = new User()

    def accessLocation = new Location()

    List<Location> accessLocations = new ArrayList<>()

    void setup() {
        user.setUsername("test@gmail.com")
        user.setId(15L)
        user.setName("test")
        user.setEnabled(1)
        user.setPassword("test")

        user2.setId(14L)
        user2.setUsername("test2@gmail.comm")
        user2.setPassword("tester")
        user2.setEnabled(1)
        testEntityManager.merge(user2)

        accessLocation.setName("test")
        accessLocation.setAddress("test")
        accessLocation.setUser(user2)
        testEntityManager.persistAndFlush(accessLocation)

        accessLocations.add(accessLocation)
    }

    def "should find by username"() {
        given: "set the data for method testing"
        testEntityManager.merge(user)

        when: "calling the testing method"
        User result = userRepository.findByUsername("test@gmail.com")

        then: "comparing expected with actual"
        user.getUsername() == result.getUsername()
    }

    def "should find all users where id not like specified"() {
        given: "set the data for method testing"
        long notExpectedId = 11L

        when: "calling the testing method"
        List<User> result = userRepository.findAllWhereIdNotLike(notExpectedId)

        then: "comparing expected with actual"
        for (User user : result) {
            user.getId() != notExpectedId
        }
    }

    def "should find all friends with read only access on location"() {
        given: "set the data for method testing"
        user.setReadOnlyLocations(accessLocations)
        testEntityManager.merge(user)

        when: "calling the testing method"
        List<User> result = userRepository.findAllReadOnlyFriendsOnLocation(accessLocation)

        then: "comparing expected with actual"
        for (User element : result) {
            element.getReadOnlyLocations().contains(accessLocation)
        }
    }

    def "should find all friends with admin access on location"() {
        given: "set the data for method testing"
        user.setAdminLocations(accessLocations)
        testEntityManager.merge(user)

        when: "calling the testing method"
        List<User> result = userRepository.findAllAdminFriendsOnLocation(accessLocation)

        then: "comparing expected with actual"
        for (User element : result) {
            element.getAdminLocations().contains(accessLocation)
        }
    }
}
