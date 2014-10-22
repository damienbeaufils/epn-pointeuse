package org.csrelais59.epn.pointeuse

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserController)
class UserControllerUnitSpec extends Specification {

    def mockedUserWebService
    def mockedUserSignInService

    def setup() {
        mockedUserWebService = Mock(UserWebService.class)
        controller.userWebService = mockedUserWebService
        mockedUserSignInService = Mock(UserSignInService.class)
        controller.userSignInService = mockedUserSignInService
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

    void "search should serialize as json found users from userWebService call"() {
        given:
        def user1 = new User(id: 1, nom: "Man", prenom: "Iron")
        def user2 = new User(id: 2, nom: "America", prenom: "Captain")
        mockedUserWebService.search(_) >> { [user1, user2] }

        when:
        controller.search()

        then:
        assertThat(response.text).isEqualTo('[{"id":1,"nom":"Man","prenom":"Iron"},{"id":2,"nom":"America","prenom":"Captain"}]')
    }

    void "signIn should call checkAndSignUser using userSignInService with user fullName from request params"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName

        when:
        controller.signIn()

        then:
        1 * mockedUserSignInService.checkAndSignUser(userFullName)
    }

    void "signIn should render signed view if userSignInService call returned true"() {
        given:
        mockedUserSignInService.checkAndSignUser(_) >> { true }

        when:
        controller.signIn()

        then:
        assertThat(view).isEqualTo('/user/signed')
    }

    void "signIn should add user fullName from request params in model if userSignInService call returned true"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName
        mockedUserSignInService.checkAndSignUser(_) >> { true }

        when:
        controller.signIn()

        then:
        assertThat(model.fullName).isEqualTo(userFullName)
    }

    void "signIn should add redirectionUrl in model from config if userSignInService call returned true"() {
        given:
        mockedUserSignInService.checkAndSignUser(_) >> { true }
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

    void "signIn should redirect to signInOrUp action if userSignInService call returned false"() {
        given:
        mockedUserSignInService.checkAndSignUser(_) >> { false }

        when:
        controller.signIn()

        then:
        assertThat(response.redirectUrl).isEqualTo('/user/signInOrUp')
    }

    void "signIn should set error flash message with fullName inside if userSignInService call returned false"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName
        mockedUserSignInService.checkAndSignUser(_) >> { false }

        when:
        controller.signIn()

        then:
        assertThat(flash.message.toString()).contains(userFullName)
    }
}
