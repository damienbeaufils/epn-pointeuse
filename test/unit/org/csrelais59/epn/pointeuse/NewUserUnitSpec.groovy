package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(NewUser)
@Build(NewUser)
class NewUserUnitSpec extends Specification {

    void "validation should pass if all attributes are correct"() {
        given:
        def newUser = new NewUser(
                male: true,
                birthYear: 1988,
                firstName: 'Damien',
                lastName: 'Beaufils',
                street: 'some street',
                zipCode: '75014',
                city: 'Paris',
                phoneNumber: '0123456789'
        )

        when:
        def isValid = newUser.validate()

        then:
        assertThat(isValid).isTrue()
    }

    void "validation should fail if male is null"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.male = null

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('male')).isTrue()
    }

    void "validation should fail if birthYear is null"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.birthYear = null

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('birthYear')).isTrue()
    }

    void "validation should fail if birthYear is below 1920"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.birthYear = 1919

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('birthYear')).isTrue()
    }

    void "validation should fail if birthYear is above 2020"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.birthYear = 2021

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('birthYear')).isTrue()
    }

    void "validation should fail if firstName is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.firstName = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('firstName')).isTrue()
    }

    void "validation should fail if lastName is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.lastName = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('lastName')).isTrue()
    }

    void "validation should fail if street is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.street = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('street')).isTrue()
    }

    void "validation should fail if zipCode is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.zipCode = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('zipCode')).isTrue()
    }

    void "validation should fail if zipCode length is less than 5 characters"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.zipCode = '1234'

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('zipCode')).isTrue()
    }

    void "validation should fail if zipCode length is more than 5 characters"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.zipCode = '123456'

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('zipCode')).isTrue()
    }

    void "validation should fail if city is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.city = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('city')).isTrue()
    }

    void "validation should fail if phoneNumber is blank"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.phoneNumber = '   '

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('phoneNumber')).isTrue()
    }

    void "validation should fail if phoneNumber is less than 10 characters"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.phoneNumber = '123456789'

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('phoneNumber')).isTrue()
    }

    void "validation should fail if phoneNumber is more than 14 characters"() {
        given:
        def newUser = NewUser.buildWithoutSave()
        newUser.phoneNumber = '012345678901234'

        when:
        def valid = newUser.validate()

        then:
        assertThat(valid).isFalse()
        assertThat(newUser.errors.hasFieldErrors('phoneNumber')).isTrue()
    }
}
