package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(ReportController)
@Mock([SignedUser, NewUser])
@Build([SignedUser, NewUser])
class ReportControllerUnitSpec extends Specification {

    void "signedUsers should return in model all signed users from database"() {
        given:
        def signedUser1 = SignedUser.build()
        def signedUser2 = SignedUser.build()

        when:
        def model = controller.signedUsers()

        then:
        assertThat(model.signedUsers).containsExactly(signedUser1, signedUser2)
    }

    void "newUsers should return in model all new users from database"() {
        given:
        def newUser1 = NewUser.build()
        def newUser2 = NewUser.build()

        when:
        def model = controller.newUsers()

        then:
        assertThat(model.newUsers).containsExactly(newUser1, newUser2)
    }
}
