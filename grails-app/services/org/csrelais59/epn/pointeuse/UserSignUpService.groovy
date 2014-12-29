package org.csrelais59.epn.pointeuse

import grails.transaction.Transactional

@Transactional
class UserSignUpService {

    def grailsApplication
    def mailService

    def void signUpNewUser(NewUser newUser) {
        newUser.save(flush: true)

        sendMailWithNewUserData(grailsApplication.config.pointeuse.newUserMailRecipients as String[], newUser)
    }

    private def sendMailWithNewUserData(String[] newUserMailRecipients, NewUser newUser) {
        String mailSubject = "[Pointeuse] Demande d'inscription : ${newUser.lastName} ${newUser.firstName}"
        String mailHtmlBody = buildHtmlBody(newUser)
        mailService.sendMail {
            async true
            to newUserMailRecipients
            subject mailSubject
            html mailHtmlBody
        }
    }

    private String buildHtmlBody(NewUser newUser) {
        "Bonjour," +
                "<br/><br/>" +
                "Demande d'inscription d'un nouvel utilisateur :" +
                "<br/>" +
                "<ul>" +
                "<li>Civilité : <b>${newUser.title.id}</b></li>" +
                "<li>Prénom : <b>${newUser.firstName}</b></li>" +
                "<li>Nom : <b>${newUser.lastName}</b></li>" +
                "<li>Année de naissance : <b>${newUser.birthYear}</b></li>" +
                "<li>Adresse :" +
                "<ul>" +
                "<li>Rue : <b>${newUser.street}</b></li>" +
                "<li>Code postal : <b>${newUser.zipCode}</b></li>" +
                "<li>Ville : <b>${newUser.city}</b></li>" +
                "</ul>" +
                "</li>" +
                "<li>Téléphone : <b>${newUser.phoneNumber}</b></li>" +
                "</ul>" +
                "<br/>" +
                "Demande d'inscription effectuée via l'application Pointeuse le ${newUser.dateCreated.format('dd/MM/yyyy à HH:mm:ss', TimeZone.getTimeZone('Europe/Paris'))}."
    }
}
