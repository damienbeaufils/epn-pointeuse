package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(SignedUser)
@Build(SignedUser)
class SignedUserUnitSpec extends Specification {

    void "validation should pass if all attributes are correct"() {
        given:
        def signedUser = new SignedUser(
                fullName: 'Damien Beaufils'
        )

        when:
        def isValid = signedUser.validate()

        then:
        assertThat(isValid).isTrue()
    }

    void "validation should fail if fullName is blank"() {
        given:
        def signedUser = SignedUser.buildWithoutSave()
        signedUser.fullName = '   '

        when:
        def valid = signedUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(signedUser.errors.hasFieldErrors('fullName')).isTrue()
    }

}
