package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "$index Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                published = "21 мая в 18:36"
            )
        }
    )
    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override fun like(postId: Long) {
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
//        val currentPost =
//        val likedPost = currentPost.copy(
//            likedByMe = !currentPost.likedByMe,
//            likes = when (currentPost.likedByMe) {
//                false ->  currentPost.likes + 1
//                true ->  currentPost.likes - 1
//            }
//        )
//        data.value = likedPost
//    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shared = true, shares = it.shares + 1)
        }
    }
//        val sharedPost = currentPost.copy(
//            shared = true,
//            shares = currentPost.shares + 1
//        )
//        data.value = sharedPost
}