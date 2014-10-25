package org.csrelais59.epn.pointeuse

import grails.transaction.Transactional

@Transactional
class UserSignUpService {

    def signUpNewUser(NewUser newUser) {
        newUser.save(flush: true)
    }

}
