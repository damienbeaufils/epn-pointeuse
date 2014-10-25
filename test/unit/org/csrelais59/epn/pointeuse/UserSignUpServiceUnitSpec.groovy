package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserSignUpService)
@Mock(NewUser)
@Build(NewUser)
class UserSignUpServiceUnitSpec extends Specification {

    void "signUpNewUser should save new user"() {
        given:
        def newUser = NewUser.buildWithoutSave()

        when:
        service.signUpNewUser(newUser)

        then:
        def newUsers = NewUser.findAll()
        assertThat(newUsers).hasSize(1)
        assertThat(newUsers[0]).isEqualToComparingOnlyGivenFields(newUser, NewUser.FIELDS_TO_USE_FOR_COMPARISON)
    }

}
