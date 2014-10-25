package org.csrelais59.epn.pointeuse

import grails.converters.JSON

class UserController {

    static defaultAction = "signInOrUp"

    def grailsApplication
    def userWebService
    def userSignInService
    def userSignUpService

    def signInOrUp() {}

    def search() {
        def foundUsers = this.userWebService.search(params.name) ?: []
        render foundUsers as JSON
    }

    def signIn() {
        def userFullName = params.fullName

        if (this.userSignInService.checkAndSignUser(userFullName)) {
            redirect(action: 'signed', params: [fullName: userFullName])
        } else {
            flash.message = "'${userFullName}' n'a pas été trouvé dans la liste des inscrits à l'@nnexe"
            render(view: 'signInOrUp')
        }
    }

    def signUp() {
        def newUser = new NewUser(params)
        if (newUser.validate()) {
            userSignUpService.signUpNewUser(newUser)
            redirect(action: 'signed', params: [fullName: "${newUser.lastName} ${newUser.firstName}"])
        } else {
            flash.message = "Veuillez renseigner tous les champs du formulaire d'inscription correctement"
            render(view: 'signInOrUp', model: [newUser: newUser])
        }
    }

    def signed() {
        render(view: 'signed', model: [fullName: params.fullName, redirectionUrl: grailsApplication.config.signedUserRedirectionUrl])
    }

}
