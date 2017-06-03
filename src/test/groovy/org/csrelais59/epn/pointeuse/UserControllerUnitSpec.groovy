package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.assertj.core.api.Assertions
import spock.lang.Specification

@TestFor(UserController)
@Build(NewUser)
class UserControllerUnitSpec extends Specification {

    static def redirectionUrl

    def mockedUserWebService
    def mockedUserSignInService
    def mockedUserSignUpService

    static doWithConfig(config) {
        redirectionUrl = 'http://www.csrelais59.org/epn/'

        config.pointeuse.signedUserRedirectionUrl = redirectionUrl
    }

    def setup() {
        mockedUserWebService = Mock(UserWebService.class)
        controller.userWebService = mockedUserWebService
        mockedUserSignInService = Mock(UserSignInService.class)
        controller.userSignInService = mockedUserSignInService
        mockedUserSignUpService = Mock(UserSignUpService.class)
        controller.userSignUpService = mockedUserSignUpService
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
        Assertions.assertThat(response.text).isEqualTo('[{"id":1,"nom":"Man","prenom":"Iron"},{"id":2,"nom":"America","prenom":"Captain"}]')
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

    void "signIn should redirect to signed action if userSignInService call returned true"() {
        given:
        mockedUserSignInService.checkAndSignUser(_) >> { true }

        when:
        controller.signIn()

        then:
        Assertions.assertThat(response.redirectedUrl).startsWith('/user/signed')
    }

    void "signIn should redirect with user fullName in params if userSignInService call returned true"() {
        given:
        params.fullName = 'Man Iron'
        mockedUserSignInService.checkAndSignUser(_) >> { true }

        when:
        controller.signIn()

        then:
        Assertions.assertThat(response.redirectedUrl).contains('fullName=Man+Iron')
    }

    void "signIn should render signInOrUp view if userSignInService call returned false"() {
        given:
        mockedUserSignInService.checkAndSignUser(_) >> { false }

        when:
        controller.signIn()

        then:
        Assertions.assertThat(view).isEqualTo('/user/signInOrUp')
    }

    void "signIn should set error flash message with fullName inside if userSignInService call returned false"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName
        mockedUserSignInService.checkAndSignUser(_) >> { false }

        when:
        controller.signIn()

        then:
        Assertions.assertThat(flash.message.toString()).contains(userFullName)
    }

    void "signUp should call signUpNewUser using userSignUpService with valid new user built from request params"() {
        given:
        def validNewUser = NewUser.buildWithoutSave()
        params << validNewUser.properties

        when:
        controller.signUp()

        then:
        1 * mockedUserSignUpService.signUpNewUser(_ as NewUser) >> { arguments ->
            Assertions.assertThat(arguments[0]).isEqualToComparingOnlyGivenFields(validNewUser, NewUser.FIELDS_TO_USE_FOR_COMPARISON)
        }
    }

    void "signUp should redirect to signed action if new user built from request params is valid"() {
        given:
        def validNewUser = NewUser.buildWithoutSave()
        params << validNewUser.properties

        when:
        controller.signUp()

        then:
        Assertions.assertThat(response.redirectedUrl).startsWith('/user/signed')
    }

    void "signUp should redirect with fullName in params from valid new user built from request params"() {
        given:
        def validNewUser = NewUser.buildWithoutSave()
        validNewUser.firstName = 'Iron'
        validNewUser.lastName = 'Man'
        params << validNewUser.properties

        when:
        controller.signUp()

        then:
        Assertions.assertThat(response.redirectedUrl).contains('fullName=Man+Iron')
    }

    void "signUp should redirect with isNewUser in params"() {
        given:
        def validNewUser = NewUser.buildWithoutSave()
        params << validNewUser.properties

        when:
        controller.signUp()

        then:
        Assertions.assertThat(response.redirectedUrl).contains('isNewUser=true')
    }

    void "signUp should set error flash message if new user built from request params is not valid"() {
        given:
        params.birthYear = 123456

        when:
        controller.signUp()

        then:
        Assertions.assertThat(flash.message).isNotNull()
    }

    void "signUp should render signInOrUp view if new user built from request params is not valid"() {
        given:
        params.birthYear = 123456

        when:
        controller.signUp()

        then:
        Assertions.assertThat(view).isEqualTo('/user/signInOrUp')
    }

    void "signUp should add new user built from request params in model if new user not valid"() {
        given:
        def invalidBirthYear = 123456
        params.birthYear = invalidBirthYear

        when:
        controller.signUp()

        then:
        Assertions.assertThat(model.newUser).isNotNull()
        Assertions.assertThat(model.newUser.birthYear).isEqualTo(invalidBirthYear)
    }

    void "signed should render signed view"() {
        when:
        controller.signed()

        then:
        Assertions.assertThat(view).isEqualTo('/user/signed')
    }

    void "signed should add user fullName from request params in model"() {
        given:
        def userFullName = 'Iron Man'
        params.fullName = userFullName

        when:
        controller.signed()

        then:
        Assertions.assertThat(model.fullName).isEqualTo(userFullName)
    }

    void "signed should add redirectionUrl in model from config"() {
        when:
        controller.signed()

        then:
        Assertions.assertThat(model.redirectionUrl).isEqualTo(redirectionUrl)
    }

    void "signed should add isNewUser from request params in model"() {
        given:
        def isNewUser = true
        params.isNewUser = isNewUser

        when:
        controller.signed()

        then:
        Assertions.assertThat(model.isNewUser).isEqualTo(isNewUser)
    }
}
