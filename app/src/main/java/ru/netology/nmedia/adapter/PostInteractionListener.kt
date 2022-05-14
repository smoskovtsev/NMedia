package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)
    fun onPostShared(post: Post)
    fun onPostDeleted(post: Post)
    fun onPostEdited(post: Post)

}