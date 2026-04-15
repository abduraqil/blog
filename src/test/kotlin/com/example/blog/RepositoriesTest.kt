package com.example.blog

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.core.mapping.AggregateReference

@DataJdbcTest
class RepositoriesTest @Autowired constructor(
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {
    @Test
    fun `When findByIdOrNull then return Article`() {
        val johnDoe = userRepository.save(User("johnDoe", "John", "Doe'"))
        val article = articleRepository.save(
            Article("Lorem", "Lorem", "dolor sit amet", AggregateReference.to(johnDoe.id!!))
        )
        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {

    }
}
