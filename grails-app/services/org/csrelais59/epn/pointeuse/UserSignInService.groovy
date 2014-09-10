package org.csrelais59.epn.pointeuse

import grails.transaction.Transactional

@Transactional
class UserSignInService {

    def userWebService

    def checkAndSignUser(String fullName) {
        if (isValidUser(fullName)) {
            new SignedUser(fullName: fullName).save(flush: true)
            return true
        }
        return false
    }

    private boolean isValidUser(String fullName) {
        boolean validUser = false
        if (fullName) {
            def lastName = fullName
            def firstName = ''

            def firstSpacePosition = fullName.indexOf(' ')
            if (firstSpacePosition > 0) {
                lastName = fullName.substring(0, firstSpacePosition)
                firstName = fullName.substring(firstSpacePosition + 1)
            }

            def foundUsers = this.userWebService.search(lastName)
            validUser = foundUsers?.find { it.nom.equalsIgnoreCase(lastName) && it.prenom.equalsIgnoreCase(firstName) }
        }
        return validUser
    }

}
