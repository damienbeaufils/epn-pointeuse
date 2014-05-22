package org.csrelais59.epn.pointeuse

import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserController)
class UserControllerUnitSpec extends Specification {

    def mockedUserWebService

    def setup() {
        mockedUserWebService = Mock(UserWebService.class)
        controller.userWebService = mockedUserWebService
    }

    def cleanup() {
    }

    void "search should call userWebService using name in request params"() {
        given:
        def name = "myName"
        params.name = name

        when:
        controller.search()

        then:
        1 * mockedUserWebService.search(name)
    }

    void "search should not call userWebService if no name in request params"() {
        given:
        params.name = null

        when:
        controller.search()

        then:
        0 * mockedUserWebService.search(_)
    }

    void "search should return null for found users if no name in request params"() {
        given:
        params.name = null

        when:
        def model = controller.search()

        then:
        assertThat(model.foundUsers).isNull()
    }

    void "search should return found users from userWebService call"() {
        given:
        params.name = 'test'
        def user1 = new User(id: 1, nom: "Man", prenom: "Iron")
        def user2 = new User(id: 2, nom: "America", prenom: "Captain")
        mockedUserWebService.search(_) >> { [user1, user2] }

        when:
        def model = controller.search()

        then:
        assertThat(model.foundUsers).containsExactly(user1, user2)
    }

    void "search should serialize users as json if request param json exists"() {
        given:
        def user1 = new User(id: 1, nom: "Man", prenom: "Iron")
        def user2 = new User(id: 2, nom: "America", prenom: "Captain")
        mockedUserWebService.search(_) >> { [user1, user2] }
        params.name = 'test'
        params.json = true

        when:
        controller.search()

        then:
        assertThat(response.text).isEqualTo('[{"id":1,"nom":"Man","prenom":"Iron"},{"id":2,"nom":"America","prenom":"Captain"}]')
    }
}
