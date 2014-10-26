package org.csrelais59.epn.pointeuse

class ReportController {

    static defaultAction = "signedUsers"

    def signedUsers() {
        [signedUsers: SignedUser.findAll()]
    }

    def newUsers() {
        [newUsers: NewUser.findAll()]
    }
}
