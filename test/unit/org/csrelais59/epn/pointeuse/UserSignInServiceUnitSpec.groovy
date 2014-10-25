package org.csrelais59.epn.pointeuse

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserSignInService)
@Mock(SignedUser)
class UserSignInServiceUnitSpec extends Specification {

    def mockedUserWebService

    def setup() {
        mockedUserWebService = Mock(UserWebService.class)
        service.userWebService = mockedUserWebService
    }

    void "checkAndSignUser should call userWebService using lastName extracted from fullName"() {
        given:
        def lastName = 'MaN'
        def fullName = lastName + ' IroN with J.A.R.V.I.S.'

        when:
        service.checkAndSignUser(fullName)

        then:
        1 * mockedUserWebService.search('MaN')
    }

    void "checkAndSignUser should call userWebService using lastName even if no firstName in fullName"() {
        given:
        def lastName = 'MaN'
        def fullName = lastName + ''

        when:
        service.checkAndSignUser(fullName)

        then:
        1 * mockedUserWebService.search('MaN')
    }

    void "checkAndSignUser should return true if user found by userWebService"() {
        given:
        def fullName = 'MaN IroN'
        mockedUserWebService.search(_) >> {
            [new User(id: 1, nom: "Man", prenom: "Ant"), new User(id: 2, nom: "Man", prenom: "Iron")]
        }

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        assertThat(result).isTrue()
    }

    void "checkAndSignUser should save new user using fullName if user found by userWebService"() {
        given:
        def fullName = 'MaN IroN'
        mockedUserWebService.search(_) >> {
            [new User(id: 1, nom: "Man", prenom: "Ant"), new User(id: 2, nom: "Man", prenom: "Iron")]
        }

        when:
        service.checkAndSignUser(fullName)

        then:
        def signedUsers = SignedUser.findAll()
        assertThat(signedUsers).hasSize(1)
        assertThat(signedUsers[0].fullName).isEqualTo(fullName)
    }

    void "checkAndSignUser should return false if no user fullName"() {
        given:
        def fullName = null

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        assertThat(result).isFalse()
    }

    void "checkAndSignUser should return false if fullName does not match with userWebService response"() {
        given:
        def fullName = 'MaN IroN'
        mockedUserWebService.search(_) >> { [new User(id: 2, nom: "America", prenom: "Captain")] }

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        assertThat(result).isFalse()
    }
}
