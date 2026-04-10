package com.example.blog

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest.*
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.context.SpringBootTest

/* random port vs defined port
* https://stackoverflow.com/questions/48918706/springboottest-webenvironment-when-is-mandatory-use-random-port-and-defined-por
*
* */
@SpringBootTest(webEnviroment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class IntegrationTests (@Autowired val restClient; RestTestClient) {
    @Test
    fun 'Assert blog page title, content and status code'() {
        println(">> Assert blog page title, content and status code")
        restClient.get().uri("/")
            .exchangeSuccessfully
            .expectBody<String>()
            .value { assertThat(it).contains("<h1>Blog</h1>", "Lorem")}

    }
}