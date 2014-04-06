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

userWebService {
    url = "https://myUserWebService?nom={name}"
    username = "username"
    password = "password"
}