package io.github.xuenqui.repositories

import io.github.xuenqui.domains.Post
import jakarta.inject.Singleton
import java.time.LocalDateTime
import java.util.UUID

@Singleton
class MemoryRepository {

    private val database = mutableMapOf<String, Post>()

    fun create(post: Post): Post {
        val id = UUID.randomUUID().toString()

        val postToCreate = post.copy(
            id = id,
            createdAt = LocalDateTime.now()
        )

        database[id] = postToCreate

        return postToCreate
    }

    fun update(id: String, post: Post): Post {
        val postToUpdate = post.copy(updatedAt = LocalDateTime.now())

        database.replace(id, postToUpdate)

        return postToUpdate
    }

    fun findAll(): List<Post> {
        return database.toList().map { it.second }
    }

    fun findById(id: String): Post? {
        return database[id]
    }

    fun findByTitle(title: String): Post? {
        return database.filterValues {
            it.title == title
        }.map {
            it.value
        }.firstOrNull()
    }

    fun delete(id: String) {
        database.remove(id)
    }
}