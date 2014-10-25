package org.csrelais59.epn.pointeuse

class NewUser {

    static final String[] FIELDS_TO_USE_FOR_COMPARISON = [
            'male', 'birthYear', 'firstName', 'lastName', 'street', 'zipCode', 'city', 'phoneNumber'
    ]

    static constraints = {
        male nullable: false
        birthYear nullable: false, min: 1920, max: 2020
        firstName blank: false
        lastName blank: false
        street blank: false
        zipCode blank: false, size: 5..5
        city blank: false
        phoneNumber blank: false, size: 10..14
    }

    Date dateCreated

    Boolean male

    Integer birthYear

    String firstName

    String lastName

    String street

    String zipCode

    String city

    String phoneNumber

}
