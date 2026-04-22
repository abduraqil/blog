package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.core.mapping.AggregateReference

// mark this as a spring config class (part of spring boot setup)
// tell spring "run whatever beans are defined here when the app starts"
@Configuration
class BlogConfiguration {

    // this creates a ban that runs right after your application starts
    // basically when the application starts it's going to execute this block below
    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {
        val johnDoe = userRepository.save(User("johnDoe", "John", "Doe"))
        articleRepository.save(
            Article(
                title = "Lorem",
                headline = "Lorem",
                content = "dolor sit amet",
                slug = "lorem",
                author = AggregateReference.to(johnDoe.id!!)
            )
        )
        println("Articles count: ${articleRepository.count()}")
    }
}