package org.csrelais59.epn.pointeuse

import grails.converters.JSON

class UserController {

    static defaultAction = "search"

    def grailsApplication
    def userWebService

    def search() {
        def users
        def searchedName = params.name

        if (searchedName) {
            users = this.userWebService.search(searchedName)
        }

        if (params.json) {
            render users as JSON
        } else {
            [foundUsers: users]
        }
    }

    def signIn() {
        def userFullName = params.fullName

        if (userFullName) {
            new SignedUser(fullName: userFullName).save(flush: true)
            render(view: 'signed', model: [fullName: userFullName, redirectionUrl: grailsApplication.config.signedUserRedirectionUrl])
        } else {
            redirect(action: 'search')
        }
    }
}
