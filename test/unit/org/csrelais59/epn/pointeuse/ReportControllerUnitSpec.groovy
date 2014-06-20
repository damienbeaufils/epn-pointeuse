package org.csrelais59.epn.pointeuse

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(ReportController)
@Mock(SignedUser)
class ReportControllerUnitSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "list should return in model all signed users from database"() {
        given:
        def user1 = new SignedUser(fullName: 'Iron Man').save(flush: true)
        def user2 = new SignedUser(fullName: 'Captain America').save(flush: true)

        when:
        def model = controller.list()

        then:
        assertThat(model.signedUsers).containsExactly(user1, user2)
    }
}
