package com.example.blog

/* random port vs defined port
* https://stackoverflow.com/questions/48918706/springboottest-webenvironment-when-is-mandatory-use-random-port-and-defined-por
*
* */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class IntegrationTests (@Autowired val restClient: RestTestClient) {

    @BeforeAll


    @Test
    // use real sentences between backticks to provide expressive test function names
    fun 'Assert blog page title, content and status code'() {
        println(">> Assert blog page title, content and status code")
        restClient.get().uri("/")
            .exchangeSuccessfully
            .expectBody<String>()
            .value { assertThat(it).contains("<h1>Blog</h1>", "Lorem")}

    }

    @Test
    fun 'Assert article page title, content and status code'() {
        println(">> TODO")
    }
}