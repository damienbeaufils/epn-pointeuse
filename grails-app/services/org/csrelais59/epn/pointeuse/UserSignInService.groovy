package org.csrelais59.epn.pointeuse

import grails.transaction.Transactional
import org.apache.commons.lang3.StringUtils

@Transactional
class UserSignInService {

    def userWebService

    def checkAndSignUser(String fullName) {
        if (isValidUser(fullName, false) || isValidUser(fullName, true)) {
            new SignedUser(fullName: fullName).save(flush: true)
            return true
        }
        return false
    }

    private boolean isValidUser(String fullName, boolean compoundLastName) {
        boolean validUser = false
        if (fullName) {
            def lastName = fullName
            def firstName = ''

            def spaceBetweenLastNameAndFirstName = StringUtils.ordinalIndexOf(fullName, ' ', compoundLastName ? 2 : 1)
            if (spaceBetweenLastNameAndFirstName > 0) {
                lastName = fullName.substring(0, spaceBetweenLastNameAndFirstName)
                firstName = fullName.substring(spaceBetweenLastNameAndFirstName + 1)
            }

            def foundUsers = this.userWebService.search(lastName)
            validUser = foundUsers?.find { it.nom.equalsIgnoreCase(lastName) && it.prenom.equalsIgnoreCase(firstName) }
        }
        return validUser
    }

}
