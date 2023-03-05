package com.example.locationsystemproject.user

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest extends Specification {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    RoleRepository roleRepository

    void setup() {
        userRepository = Mockito.mock(UserRepository.class)
        bCryptPasswordEncoder = new BCryptPasswordEncoder()
        roleRepository = Mockito.mock(RoleRepository.class)
        userService = new UserServiceImpl(userRepository,roleRepository,bCryptPasswordEncoder)
    }

    def "should find user by username"() {
        given: "set the data for method testing"
        def user1 = new User()
        user1.setUsername("boyarchuk.vitaliy@gmail.com")
        Mockito.when(userRepository.findByUsername("boyarchuk.vitaliy@gmail.com")).thenReturn(user1)

        when: "calling the testing method"
        User user = userService.findByUserName("boyarchuk.vitaliy@gmail.com")

        then: "comparing expected with actual"
        user.getUsername() == user1.getUsername()
    }
}
