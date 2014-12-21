package org.csrelais59.epn.pointeuse

import grails.test.mixin.TestFor
import org.apache.http.auth.AuthScope
import org.apache.http.impl.client.BasicCredentialsProvider
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@TestFor(UserWebService)
class UserWebServiceUnitSpec extends Specification {

    def mockedRestTemplate

    def setup() {
        mockedRestTemplate = Mock(RestTemplate.class)
        service.restTemplate = mockedRestTemplate
    }

    def cleanup() {
    }

    void "initRestTemplate should create a new RestTemplate"() {
        when:
        service.initRestTemplate()

        then:
        assertThat(service.restTemplate).isNotNull()
        assertThat(service.restTemplate).isInstanceOf(RestTemplate.class)
    }

    void "initRestTemplate should have MappingJackson2HttpMessageConverter in created RestTemplate"() {
        when:
        service.initRestTemplate()

        then:
        def hasJackson2Converter = false
        service.restTemplate.messageConverters.each {
            if (it instanceof MappingJackson2HttpMessageConverter) {
                hasJackson2Converter = true
            }
        }
        assertThat(hasJackson2Converter).isTrue()
    }

    void "initRestTemplate should set basic authentication on any scope using username and password from configuration"() {
        given:
        def username = 'myUsername'
        def password = 'myPassword'
        def mockedGrailsApplication = Mock(GrailsApplication.class)
        def config = new ConfigObject()
        config.pointeuse.userWebService.username = username
        config.pointeuse.userWebService.password = password
        mockedGrailsApplication.config >> { config }
        service.grailsApplication = mockedGrailsApplication

        when:
        service.initRestTemplate()

        then:
        def factory = (HttpComponentsClientHttpRequestFactory) service.restTemplate.requestFactory
        def credentialsProvider = (BasicCredentialsProvider) ReflectionTestUtils.getField(factory.httpClient, 'credentialsProvider')
        def credentials = credentialsProvider.getCredentials(AuthScope.ANY)
        assertThat(credentials.userPrincipal.name).isEqualTo(username)
        assertThat(credentials.password).isEqualTo(password)
    }

    void "search should use webservice url from config"() {
        given:
        def name = 'MAN'
        def userWebServiceUrl = 'http://myUserWebService.mock?nom={name}'
        def mockedGrailsApplication = Mock(GrailsApplication.class)
        def config = new ConfigObject()
        config.pointeuse.userWebService.url = userWebServiceUrl
        mockedGrailsApplication.config >> { config }
        service.grailsApplication = mockedGrailsApplication

        when:
        service.search(name)

        then:
        1 * mockedRestTemplate.getForEntity(userWebServiceUrl, _, _)
    }

    void "search should be waiting an array of User from webservice"() {
        given:
        def name = 'MAN'

        when:
        service.search(name)

        then:
        1 * mockedRestTemplate.getForEntity(_, User[].class, _)
    }

    void "search should use call webservice with given name"() {
        given:
        def name = 'MAN'

        when:
        service.search(name)

        then:
        1 * mockedRestTemplate.getForEntity(_, _, name)
    }

    void "search should return an empty collection if webservice returns no users"() {
        given:
        def name = 'MAN'
        mockedRestTemplate.getForEntity(_, _, _) >> { new ResponseEntity([], HttpStatus.OK) }

        when:
        def result = service.search(name)

        then:
        assertThat(result).hasSize(0)

    }

    void "search should return users bind from webservice response"() {
        given:
        def name = 'MAN'
        def user1 = new User(id: 1, nom: "Man", prenom: "Iron")
        def user2 = new User(id: 2, nom: "America", prenom: "Captain")
        mockedRestTemplate.getForEntity(_, _, _) >> { new ResponseEntity([user1, user2], HttpStatus.OK) }

        when:
        def result = service.search(name)

        then:
        assertThat(result).containsExactly(user1, user2)
    }

    void "search should never call webservice and return an empty collection if name parameter is null"() {
        given:
        def name = null

        when:
        def result = service.search(name)

        then:
        0 * mockedRestTemplate.getForEntity(_, _, _)
        assertThat(result).hasSize(0)
    }

    void "search should never call webservice and return an empty collection if name parameter length is less than 3 characters"() {
        given:
        def name = 'DB'

        when:
        def result = service.search(name)

        then:
        0 * mockedRestTemplate.getForEntity(_, _, _)
        assertThat(result).hasSize(0)
    }
}
