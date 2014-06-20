package org.csrelais59.epn.pointeuse

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserController)
@Mock(SignedUser)
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

    void "signIn should redirect to search action if no fullName in request params"() {
        given:
        params.fullName = null

        when:
        controller.signIn()

        then:
        assertThat(response.redirectUrl).isEqualTo('/user/search')
    }

    void "signIn should save new user using fullName in request params"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName

        when:
        controller.signIn()

        then:
        assertThat(SignedUser.findAll()).extracting('fullName').containsExactly(userFullName)
    }

    void "signIn should render signed view if fullName exists in request params"() {
        given:
        params.fullName = 'Iron Man'

        when:
        controller.signIn()

        then:
        assertThat(view).isEqualTo('/user/signed')
    }

    void "signIn should add user fullName in model if fullName exists in request params"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName

        when:
        controller.signIn()

        then:
        assertThat(model.fullName).isEqualTo(userFullName)
    }

    void "signIn should add redirectionUrl in model from config if fullName exists in request params"() {
        given:
        params.fullName = 'Iron Man'
        def redirectionUrl = 'http://www.csrelais59.org/epn/'
        def mockedGrailsApplication = Mock(GrailsApplication.class)
        def config = new ConfigObject()
        config.signedUserRedirectionUrl = redirectionUrl
        mockedGrailsApplication.config >> { config }
        controller.grailsApplication = mockedGrailsApplication

        when:
        controller.signIn()

        then:
        assertThat(model.redirectionUrl).isEqualTo(redirectionUrl)
    }
}
