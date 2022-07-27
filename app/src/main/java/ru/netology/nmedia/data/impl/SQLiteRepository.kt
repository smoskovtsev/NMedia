package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostsDao
import ru.netology.nmedia.dto.Post

class SQLiteRepository(
    private val dao: PostsDao
) : PostRepository {

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(dao.getAll())

    override fun like(postId: Long) {
        dao.likedById(postId)
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = when (it.likedByMe) {
                    false -> it.likes + 1
                    true -> it.likes - 1
                }
            )
       }
    }

    override fun share(postId: Long) {
        dao.shareById(postId)
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shared = true, shares = it.shares + 1)
        }
    }

    override fun delete(postId: Long) {
        dao.removeById(postId)
        data.value = posts.filter {it.id != postId}
    }

    override fun addUpdatePost(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0L) {
            listOf(saved) + posts
        } else posts.map {
                if (it.id != id) it else saved
            }
    }
}