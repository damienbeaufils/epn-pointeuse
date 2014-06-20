package org.csrelais59.epn.pointeuse

class ReportController {

    static defaultAction = "list"

    def list() {

        [signedUsers : SignedUser.findAll()]

    }
}
