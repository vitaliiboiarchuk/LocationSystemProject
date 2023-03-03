package com.example.locationsystemproject.user

import com.example.locationsystemproject.location.Location
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
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

    List<User> userList = new ArrayList<>()

    def readOnlyLocation = new Location()

    List<Location> readOnlyLocations = new ArrayList<>()

    def adminLocation = new Location()

    List<Location> adminLocations = new ArrayList<>()



    void setup() {
        user.setUsername("test@gmail.com")
        user.setId(15L)
        user.setName("test")
        user.setEnabled(1)
        user.setPassword("test")
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
        testEntityManager.merge(user)
        userRepository = Mockito.mock(UserRepository.class)
        Mockito.when(userRepository.findAllWhereIdNotLike(14L)).thenReturn(userList)

        when: "calling the testing method"
        List<User> result = userRepository.findAllWhereIdNotLike(14L)

        then: "comparing expected with actual"
        userList == result
    }

    def "should find all friends on location with read only access"() {
        given: "set the data for method testing"
        readOnlyLocations.add(readOnlyLocation)
        user.setReadOnlyLocations(readOnlyLocations)
        testEntityManager.merge(user)
        userList.add(user)
        userRepository = Mockito.mock(UserRepository.class)
        Mockito.when(userRepository.findAllReadOnlyFriendsOnLocation(readOnlyLocation)).thenReturn(userList)

        when: "calling the testing method"
        List<User> result = userRepository.findAllReadOnlyFriendsOnLocation(readOnlyLocation)

        then: "comparing expected with actual"
        userList == result
    }

    def "should find all friends on location with admin access"() {
        given: "set the data for method testing"
        adminLocations.add(adminLocation)
        user.setAdminLocations(adminLocations)
        testEntityManager.merge(user)
        userList.add(user)
        userRepository = Mockito.mock(UserRepository.class)
        Mockito.when(userRepository.findAllAdminFriendsOnLocation(adminLocation)).thenReturn(userList)

        when: "calling the testing method"
        List<User> result = userRepository.findAllAdminFriendsOnLocation(adminLocation)

        then: "comparing expected with actual"
        userList == result

    }
}
