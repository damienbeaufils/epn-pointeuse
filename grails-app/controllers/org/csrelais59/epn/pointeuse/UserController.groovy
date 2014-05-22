package org.csrelais59.epn.pointeuse

import grails.converters.JSON

class UserController {

    static defaultAction = "search"

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
}
