package ru.netology.nmedia.db

import ru.netology.nmedia.dto.Post

internal fun PostsEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    videoUrl = videoUrl,
    likes = likes,
    shares = shares,
    views = views,
    likedByMe = likedByMe,
    shared = shared
)

internal fun Post.toEntity() = PostsEntity(
    id = id,
    author = author!!,
    content = content!!,
    published = published!!,
    videoUrl = videoUrl,
    likes = likes,
    shares = shares,
    views = views,
    likedByMe = likedByMe,
    shared = shared
)