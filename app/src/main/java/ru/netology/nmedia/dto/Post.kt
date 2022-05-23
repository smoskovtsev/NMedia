package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val videoUrl: String = "",
    var likes: Int = 999,
    var shares: Int = 99990,
    var views: Int = 5,
    var likedByMe: Boolean = false,
    var shared: Boolean = false
)
