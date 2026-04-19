package com.example.blog

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

/* @Controller
* this tells spring that this class should handle web requests
* it registers this class in Spring's Application Context
* enables Spring MVC to scan it for requests @Get Mapping
* */
@Controller
class HtmlController(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val properties: BlogProperties,
) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = properties.title
        model["banner"] = properties.banner
        // call the method on the **class**, not instance, not ArticleRepo but articleRepo
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc()
            .map { it.render() }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = articleRepository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

        val renderedArticle = article.render()
        model["title"] = renderedArticle.title
        model["article"] = renderedArticle
        return "article"
    }

    private fun Article.render(): RenderedArticle {
        val author = userRepository.findById(author.id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Author") }

        return RenderedArticle(
            slug,
            title,
            headline,
            content,
            author,
            addedAt.format()
        )
    }

    data class RenderedArticle(
        val slug: String,
        val title: String,
        val headline: String,
        val content: String,
        val author: User,
        val addedAt: String
    )
}

/*
* spring follows the MVC Pattern
* Model -> data (your model)
* View -> HTML Template (blog.html)?
* Controller -> handles requests
* */