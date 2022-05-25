package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository =
        FilePostRepository(application)

    val data get() = repository.data //or val data by repository :: data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEventCreate = SingleLiveEvent<Unit>()
    val navigateToPostContentScreenEventEdit = SingleLiveEvent<Unit>()
    val currentPost = MutableLiveData<Post?>(null)
    val playVideoUrl = SingleLiveEvent<String>()

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

    fun onAddClicked() {
        navigateToPostContentScreenEventCreate.call()
    }

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onPostShared(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.content
    }

    override fun onPostDeleted(post: Post) = repository.delete(post.id)

    override fun onPostEdited(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEventEdit.call()
    }

    override fun onVideoPlayClicked(post: Post) {
        playVideoUrl.value = post.videoUrl
    }

    // endregion PostInteractionListener
}