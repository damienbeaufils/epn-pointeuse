databaseChangeLog = {
    // mvn grails:exec -Dcommand=dbm-generate-gorm-changelog -Dargs=create_changelog.groovy
    include file: "create_changelog.groovy"
}
