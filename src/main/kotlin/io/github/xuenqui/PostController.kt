package io.github.xuenqui

import io.github.xuenqui.domains.Author
import io.github.xuenqui.domains.Post
import io.github.xuenqui.repositories.MemoryRepository
import io.micronaut.grpc.annotation.GrpcService
import io.github.xuenqui.Author as AuthorGrpc
import io.github.xuenqui.Post as PostGrpc

@GrpcService
class PostController(
    private val memoryRepository: MemoryRepository
) : PostServiceGrpcKt.PostServiceCoroutineImplBase() {

    override suspend fun createPost(request: PostGrpc): PostGrpc {
        val postToCreate = request.toDomain()
        val postCreated = memoryRepository.create(postToCreate)

        return postCreated.toGrpc().build()
    }
}

fun PostGrpc.toDomain(): Post =
    Post(
        title = this.title,
        content = this.content,
        location = this.location,
        tags = this.tagsList.map { it },
        author = this.author.toDomain()
    )

fun AuthorGrpc.toDomain(): Author =
    Author(
        name = this.name,
        email = this.email,
        nickname = this.nickname
    )

fun Post.toGrpc(): PostGrpc.Builder =
    PostGrpc.newBuilder()
        .setId(this.id)
        .setContent(this.content)
        .setTitle(this.title)
        .setAuthor(this.author.toGrpc())
        .addAllTags(this.tags)
        .setLocation(this.location)
        .setCreatedAt(this.createdAt.toString())
        .setUpdatedAt(this.updatedAt.toString())

fun Author.toGrpc(): AuthorGrpc.Builder =
    AuthorGrpc
        .newBuilder()
        .setEmail(this.email)
        .setName(this.name)
        .setNickname(this.nickname)