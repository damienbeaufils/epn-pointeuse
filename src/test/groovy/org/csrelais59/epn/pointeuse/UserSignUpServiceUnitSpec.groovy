package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.plugins.mail.MailMessageBuilder
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.assertj.core.api.Assertions
import spock.lang.Specification

@TestFor(UserSignUpService)
@Mock(NewUser)
@Build(NewUser)
class UserSignUpServiceUnitSpec extends Specification {

    static def newUserMailRecipients

    def mockedMailMessageBuilder
    NewUser newUser

    static doWithConfig(config) {
        newUserMailRecipients = ['tony@starkindustries.com', 'ironman@starkindustries.com']

        config.pointeuse.newUserMailRecipients = newUserMailRecipients
    }

    void setup() {
        mockedMailMessageBuilder = Mock(MailMessageBuilder.class)

        def mockedMailService = new Object()
        mockedMailService.metaClass.sendMail = { callable ->
            callable.delegate = mockedMailMessageBuilder
            callable.resolveStrategy = Closure.DELEGATE_FIRST
            callable.call()
        }
        service.mailService = mockedMailService

        newUser = NewUser.buildWithoutSave()
    }

    void "signUpNewUser should save new user"() {
        when:
        service.signUpNewUser(newUser)

        then:
        def newUsers = NewUser.findAll()
        Assertions.assertThat(newUsers).hasSize(1)
        Assertions.assertThat(newUsers[0]).isEqualToComparingOnlyGivenFields(newUser, NewUser.FIELDS_TO_USE_FOR_COMPARISON)
    }

    void "signUpNewUser should send mail to recipient address from configuration"() {
        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailMessageBuilder.to(newUserMailRecipients.toArray(new String[0]))
    }

    void "signUpNewUser should send mail with specific subject containing new user firstName and lastName"() {
        given:
        newUser.firstName = 'Tony'
        newUser.lastName = 'Stark'

        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailMessageBuilder.subject("[Pointeuse] Demande d'inscription : Stark Tony")
    }

    void "signUpNewUser should send mail with html body containing all user data"() {
        given:
        def now = new Date()
        def nowAsString = now.format('dd/MM/yyyy à HH:mm:ss', TimeZone.getTimeZone('Europe/Paris'))
        newUser = NewUser.buildWithoutSave(
                title: NewUser.Title.MISTER,
                birthYear: 1988,
                firstName: 'Damien',
                lastName: 'Beaufils',
                street: 'some street',
                zipCode: '75014',
                city: 'Paris',
                phoneNumber: '0123456789',
                dateCreated: now
        )

        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailMessageBuilder.html(
                "Bonjour," +
                        "<br/><br/>" +
                        "Demande d'inscription d'un nouvel utilisateur :" +
                        "<br/>" +
                        "<ul>" +
                        "<li>Civilité : <b>Monsieur</b></li>" +
                        "<li>Prénom : <b>Damien</b></li>" +
                        "<li>Nom : <b>Beaufils</b></li>" +
                        "<li>Année de naissance : <b>1988</b></li>" +
                        "<li>Adresse :" +
                        "<ul>" +
                        "<li>Rue : <b>some street</b></li>" +
                        "<li>Code postal : <b>75014</b></li>" +
                        "<li>Ville : <b>Paris</b></li>" +
                        "</ul>" +
                        "</li>" +
                        "<li>Téléphone : <b>0123456789</b></li>" +
                        "</ul>" +
                        "<br/>" +
                        "Demande d'inscription effectuée via l'application Pointeuse le ${nowAsString}."
        )
    }

    void "signUpNewUser should send mail asynchronously"() {
        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailMessageBuilder.async(true)
    }

}
