package io.github.xuenqui.domains

import java.time.LocalDateTime

data class Post(
    val id: String? = null,
    val title: String,
    val content: String,
    val location: String? = null,
    val tags: List<String> = emptyList(),
    val author: Author,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)

data class Author(
    val name: String,
    val email: String,
    val nickname: String
)
