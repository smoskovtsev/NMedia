package ru.netology.nmedia.db

import ru.netology.nmedia.dto.Post

interface PostsDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likedById(id: Long)
    fun removeById(id: Long)
    fun shareById(id: Long)
}