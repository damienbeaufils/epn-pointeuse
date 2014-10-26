package org.csrelais59.epn.pointeuse

import grails.buildtestdata.mixin.Build
import grails.plugin.mail.MailService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserSignUpService)
@Mock(NewUser)
@Build(NewUser)
class UserSignUpServiceUnitSpec extends Specification {

    def mockedMailService
    NewUser newUser

    void setup() {
        mockedMailService = Mock(MailService.class)
        service.mailService = mockedMailService
        newUser = NewUser.buildWithoutSave()
    }

    void "signUpNewUser should save new user"() {
        when:
        service.signUpNewUser(newUser)

        then:
        def newUsers = NewUser.findAll()
        assertThat(newUsers).hasSize(1)
        assertThat(newUsers[0]).isEqualToComparingOnlyGivenFields(newUser, NewUser.FIELDS_TO_USE_FOR_COMPARISON)
    }

    void "signUpNewUser should send mail to recipient address from configuration"() {
        given:
        def newUserMailRecipient = 'tony@starkindustries.com'
        def mockedGrailsApplication = Mock(GrailsApplication.class)
        def config = new ConfigObject()
        config.newUserMailRecipient = newUserMailRecipient
        mockedGrailsApplication.config >> { config }
        service.grailsApplication = mockedGrailsApplication

        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailService.sendMail(_ as Closure) >> { arguments ->
            def callable = mockSendMailClosure(arguments[0])
            callable.metaClass.to { String to ->
                assertThat(to).isEqualTo(newUserMailRecipient)
                return
            }
            callable.call()
        }
    }

    void "signUpNewUser should send mail with specific subject containing new user firstName and lastName"() {
        given:
        newUser.firstName = 'Tony'
        newUser.lastName = 'Stark'

        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailService.sendMail(_ as Closure) >> { arguments ->
            def callable = mockSendMailClosure(arguments[0])
            callable.metaClass.subject { String subject ->
                assertThat(subject).isEqualTo("[Pointeuse] Demande d'inscription : Stark Tony")
                return
            }
            callable.call()
        }
    }

    void "signUpNewUser should send mail with html body containing all user data"() {
        given:
        def now = new Date()
        def nowAsString = now.format('dd/MM/yyyy à HH:mm:ss', TimeZone.getTimeZone('Europe/Paris'))
        newUser = new NewUser(
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
        1 * mockedMailService.sendMail(_ as Closure) >> { arguments ->
            def callable = mockSendMailClosure(arguments[0])
            callable.metaClass.html { String html ->
                assertThat(html).isEqualTo(
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
                return
            }
            callable.call()
        }
    }

    void "signUpNewUser should send mail asynchronously"() {
        when:
        service.signUpNewUser(newUser)

        then:
        1 * mockedMailService.sendMail(_ as Closure) >> { arguments ->
            def callable = mockSendMailClosure(arguments[0])
            callable.metaClass.async { Boolean async ->
                assertThat(async).isTrue()
                return
            }
            callable.call()
        }
    }

    private Closure mockSendMailClosure(Closure callable) {
        callable.metaClass.async { Boolean async -> return }
        callable.metaClass.subject { String subject -> return }
        callable.metaClass.html { String html -> return }
        callable.metaClass.to { String to -> return }
        callable.resolveStrategy = Closure.TO_SELF
        callable
    }
}
