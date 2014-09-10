package org.csrelais59.epn.pointeuse

import grails.converters.JSON

class UserController {

    static defaultAction = "search"

    def grailsApplication
    def userWebService
    def userSignInService

    def search() {
        def users = this.userWebService.search(params.name)

        if (params.json) {
            render users as JSON
        } else {
            [foundUsers: users]
        }
    }

    def signIn() {
        def userFullName = params.fullName

        if (this.userSignInService.checkAndSignUser(userFullName)) {
            render(view: 'signed', model: [fullName: userFullName, redirectionUrl: grailsApplication.config.signedUserRedirectionUrl])
        } else {
            flash.message = "'${userFullName}' n'a pas été trouvé dans la liste des inscrits à l'EPN"
            redirect(action: 'search')
        }
    }

}
