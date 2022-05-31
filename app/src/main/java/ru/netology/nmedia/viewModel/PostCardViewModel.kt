package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostCardViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val playVideoUrl = SingleLiveEvent<String>()
    val removePost = SingleLiveEvent<Unit>()

    fun getPostById(postId: Long) : Post? =
        repository.getById(postId)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "TestAuthor",
            content = content,
            published = "Today"
        )
        repository.addUpdatePost(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) {
        repository.like(post.id)
    }

    override fun onPostShared(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.content
    }

    override fun onPostDeleted(post: Post) {
        repository.delete(post.id)
    }

    override fun onPostEdited(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onVideoPlayClicked(post: Post) {
        playVideoUrl.value = post.videoUrl
    }

    override fun onPostClicked(post: Post) {
    }

}