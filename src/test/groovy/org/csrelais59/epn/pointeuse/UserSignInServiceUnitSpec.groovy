package org.csrelais59.epn.pointeuse

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.assertj.core.api.Assertions
import spock.lang.Specification

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
        (1.._) * mockedUserWebService.search('MaN')
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
        Assertions.assertThat(result).isTrue()
    }

    void "checkAndSignUser should save new user using uppercase fullName if user found by userWebService"() {
        given:
        def fullName = 'MaN IroN'
        mockedUserWebService.search(_) >> {
            [new User(id: 1, nom: "Man", prenom: "Ant"), new User(id: 2, nom: "Man", prenom: "Iron")]
        }

        when:
        service.checkAndSignUser(fullName)

        then:
        def signedUsers = SignedUser.findAll()
        Assertions.assertThat(signedUsers).hasSize(1)
        Assertions.assertThat(signedUsers[0].fullName).isEqualTo('MAN IRON')
    }

    void "checkAndSignUser should return false if no user fullName"() {
        given:
        def fullName = null

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        Assertions.assertThat(result).isFalse()
        0 * mockedUserWebService.search(_)
    }

    void "checkAndSignUser should return false if fullName does not match with userWebService response"() {
        given:
        def fullName = 'MaN IroN'
        mockedUserWebService.search(_) >> { [new User(id: 2, nom: "America", prenom: "Captain")] }

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        Assertions.assertThat(result).isFalse()
    }

    void "checkAndSignUser should return true when user have a compound last name"() {
        given:
        def fullName = 'De Backer Daniel'
        mockedUserWebService.search(_) >> { [new User(id: 3, nom: "De Backer", prenom: "Daniel")] }

        when:
        def result = service.checkAndSignUser(fullName)

        then:
        Assertions.assertThat(result).isTrue()
    }
}
