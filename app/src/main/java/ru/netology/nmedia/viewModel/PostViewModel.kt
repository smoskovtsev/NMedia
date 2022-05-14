package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data //or val data by repository :: data

    val currentPost = MutableLiveData<Post?>(null)

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

    fun onCancelButtonClicked() {
        val post = currentPost.value?.copy()
        if (post != null) {
            repository.addUpdatePost(post)
        }
        currentPost.value = null
    }

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onPostShared(post: Post) = repository.share(post.id)

    override fun onPostDeleted(post: Post) = repository.delete(post.id)

    override fun onPostEdited(post: Post) {
        currentPost.value = post
    }

    // endregion PostInteractionListener
}