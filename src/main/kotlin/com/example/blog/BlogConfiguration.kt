package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.mapping.AggregateReference

// mark this as a spring config class (part of spring boot setup)
// tell spring "run whatever beans are define dhere when the app starts"
@Configuration
class BlogConfiguration {

    // this creates a ban that runs right after your application starts
    // basically, when app starts, execute this block below
    @Bean
    fun databaseInitialzier(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {
        val johnDoe = userRepository.save(User("johnDoe", "John", "Doe"))
        articleRepository.save(
            Article(
                title = "Lorem",
                headline = "Lorem",
                content = "dolor sit amet",
                author = AggregateReference.to(johnDoe.id!!)
            )
        )
    }
}