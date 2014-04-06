package org.csrelais59.epn.pointeuse

class UserController {

    static defaultAction = "search"

    def userWebService

    def search() {
        def users
        def searchedName = params.name

        if (searchedName) {
            users = this.userWebService.search(searchedName)
        }

        [foundUsers: users]
    }
}
