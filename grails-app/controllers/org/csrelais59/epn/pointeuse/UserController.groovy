package org.csrelais59.epn.pointeuse

import grails.converters.JSON

class UserController {

    static defaultAction = "signInOrUp"

    def grailsApplication
    def userWebService
    def userSignInService

    def signInOrUp() {}

    def search() {
        def users = this.userWebService.search(params.name)
        render users as JSON
    }

    def signIn() {
        def userFullName = params.fullName

        if (this.userSignInService.checkAndSignUser(userFullName)) {
            render(view: 'signed', model: [fullName: userFullName, redirectionUrl: grailsApplication.config.signedUserRedirectionUrl])
        } else {
            flash.message = "'${userFullName}' n'a pas été trouvé dans la liste des inscrits à l'@nnexe"
            redirect(action: 'signInOrUp')
        }
    }

}
