package com.example.locationsystemproject.user

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoleRepositoryTest extends Specification {

    @Autowired
    TestEntityManager testEntityManager

    @Autowired
    RoleRepository roleRepository

    def "should find by name"() {
        given: "set the data for method testing"
        Role role = new Role()
        role.setName("ROLE_USER")
        testEntityManager.persist(role)

        when: "calling the testing method"
        Role result = roleRepository.findByName("ROLE_USER")

        then: "comparing expected with actual"
        role.getName() == result.getName()
    }
}
