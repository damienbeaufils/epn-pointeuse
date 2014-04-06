package org.csrelais59.epn.pointeuse

import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

import javax.annotation.PostConstruct

class UserWebService {

    def grailsApplication

    private RestTemplate restTemplate;

    @PostConstruct
    private void initRestTemplate() {
        def userWebServiceConfig = grailsApplication.config.userWebService
        def clientHttpRequestFactory = this.createBasicAuthenticationHttpRequestFactory(userWebServiceConfig.username, userWebServiceConfig.password)
        restTemplate = new RestTemplate(clientHttpRequestFactory)
    }

    private ClientHttpRequestFactory createBasicAuthenticationHttpRequestFactory(String username, String password) {
        def credentials = new UsernamePasswordCredentials(username, password)
        def credentialsProvider = new BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        def httpClient = HttpClientBuilder.create()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .setDefaultCredentialsProvider(credentialsProvider)
                .build()
        return new HttpComponentsClientHttpRequestFactory(httpClient)
    }

    def search(name) {
        def response = this.restTemplate.getForEntity(grailsApplication.config.userWebService.url, User[].class, name)
        return response?.body
    }

}