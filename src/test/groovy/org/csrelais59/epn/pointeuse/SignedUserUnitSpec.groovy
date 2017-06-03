package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.assertj.core.api.Assertions
import spock.lang.Specification

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
        Assertions.assertThat(isValid).isTrue()
    }

    void "validation should fail if fullName is blank"() {
        given:
        def signedUser = SignedUser.buildWithoutSave()
        signedUser.fullName = '   '

        when:
        def valid = signedUser.validate()

        then:
        Assertions.assertThat(valid).isFalse()
        Assertions.assertThat(signedUser.errors.hasFieldErrors('fullName')).isTrue()
    }

}
