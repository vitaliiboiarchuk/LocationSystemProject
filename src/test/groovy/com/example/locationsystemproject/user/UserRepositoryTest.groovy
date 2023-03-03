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

    
    void setup() {
        user.setUsername("test@gmail.com")
        user.setId(15L)
        user.setName("test")
        user.setEnabled(1)
        user.setPassword("test")
        testEntityManager.merge(user)
    }

    def "should find by username"() {
        when: "calling the testing method"
        User result = userRepository.findByUsername("test@gmail.com")

        then: "comparing expected with actual"
        user.getUsername() == result.getUsername()
    }
}
