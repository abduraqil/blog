package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.util.Optional
import org.springframework.web.servlet.function.RequestPredicates.accept

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var articleRepository: ArticleRepository

    /*
    * AggregateReference is basically "a lightweight wrapper around an ID that represents a relationship"
    * instead of storing the full object, you store a **reference** to another aggregate by its ID
    */
    @Test
    fun `List articles`() {
        // johnDoe -> a full object in memory
        // authorRef -> just a reference saying: "this points to User with Id = 1"
        val johnDoe = User("johnDoe", "John", "Doe", id = 1L)
        val authorRef = AggregateReference.to<User, Long>(1L)
        val lorem5Article = Article("Lorem", "Lorem", "dolor sit amet", authorRef)
        val ipsumArticle = Article("Ipsum", "Ipsum", "dolor sit amet", authorRef)

        every { userRepository.findById(1L) } returns Optional.of(johnDoe)
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(lorem5Article, ipsumArticle)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].author.login").value(johnDoe.login))
            .andExpect(jsonPath("$.[0].slug").value(lorem5Article))
            .andExpect(jsonPath("$.[1].author.login").value(johnDoe.login))
            .andExpect(jsonPath("$.[1].slug").value(ipsumArticle))
    }

    @Test
    fun `List users`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        val janeDoe = User("janeDoe", "Jane", "Doe")

        every { userRepository.findAll() } returns listOf(johnDoe, janeDoe)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].login").value(johnDoe.login))
            .andExpect(jsonPath("$.[1].login").value(janeDoe.login))
    }
}