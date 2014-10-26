environments {
    production {
        dataSource {
            dbCreate = "create-drop"
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
}

grails {
    mail {
        // see mail Grails plugin configuration (http://grails.org/plugin/mail)
        host = "smtp.gmail.com"
        port = 465
        username = "youracount@gmail.com"
        password = "yourpassword"
        props = ["mail.smtp.auth"                  : "true",
                 "mail.smtp.socketFactory.port"    : "465",
                 "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback": "false"]
    }
}

userWebService {
    url = "https://myUserWebService?nom={name}"
    username = "username"
    password = "password"
}

signedUserRedirectionUrl = 'http://www.csrelais59.org/epn/'

newUserMailRecipient = 'damien.beaufils@gmail.com'
