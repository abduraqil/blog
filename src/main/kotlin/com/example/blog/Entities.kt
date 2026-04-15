package com.example.blog

/*
* To fetch our data with spring data jdbc, we use immutable data classes (more idiomatic approach in Kotlin)
* create model by using kotlin primary constructor conciose syntax and data classes which allow
* declaring at the same time the properties and the constructor parameters
* */

import org.h2.engine.User
import org.springframework.context.annotation.Description
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

// @Table annotation maps the classes to the database tables, and @Column is used for FK relations
// Spring Data JDBC uses `AggregateReference` to reference FK relatiosn, which only store IDs
// rather than loading the entire related entity
@Table("article")
data class Article (
    val title: String,
    val headline: String,
    val content: String,
    @Column("author_id")
    val author: AggregateReference<User, Long>,
    val slug: String = title.toSlug(), // we use string.toSlug() to provide a default arg to the slug parameter of the atricle constructor
    val addedAt: LocalDateTime = LocalDateTime.now(),
    @Id val id: Long? = null
)

@Table("users")
data class User (
    val login: String,
    val firstName: String,
    val lastName: String,
    val description: String? = null,
    @Id val id: Long? = null
)






