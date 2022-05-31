package ru.netology.nmedia.ui

import android.widget.PopupMenu
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

fun PostBinding.listen(post: Post, listener: PostInteractionListener) {

    val popupMenu by lazy {
            PopupMenu(root.context, menu).apply {
               inflate(R.menu.options_post)
               setOnMenuItemClickListener { menuItem ->
                   when (menuItem.itemId) {
                       R.id.remove -> {
                           listener.onPostDeleted(post)
                           true
                       }
                       R.id.edit -> {
                           listener.onPostEdited(post)
                           true
                       }
                       else -> false
                   }
               }
            }
        }

    like.setOnClickListener { listener.onLikeClicked(post) }
    share.setOnClickListener { listener.onPostShared(post) }
    menu.setOnClickListener{ popupMenu.show() }
    videoPlayButton.setOnClickListener { listener.onVideoPlayClicked(post) }
    videoContent.setOnClickListener { listener.onVideoPlayClicked(post) }
    content.setOnClickListener { listener.onPostClicked(post) }

}

fun PostBinding.bind(post: Post) {
    author.text = post.author
    content.text = post.content
    published.text = post.published
    like.text = likesSharesDisplay(post.likes)
    like.isChecked = post.likedByMe
    share.text = likesSharesDisplay(post.shares)
    share.isChecked = post.shared
    visibility.text = likesSharesDisplay(post.views)
    objectVideo.visibility =
        if (post.videoUrl.isBlank()) android.view.View.GONE else android.view.View.VISIBLE
}

private fun likesSharesDisplay (amount: Int): String {
    return when {
        (amount < 1000) -> "$amount"
        (amount < 10000) && (amount % 1000) < 100 -> "${amount/1000}K"
        (amount < 10000) -> "${amount/1000}.${(amount % 1000)/100}K"
        (amount < 1_000_000) -> "${amount/1000}K"
        (amount == 1_000_000) -> "${amount/1_000_000}M"
        (amount > 1_000_000) && (amount % 1_000_000) < 100_000 -> "${amount/1_000_000}M"
        else -> "${amount/1_000_000}.${(amount % 1_000_000)/100_000}M"
    }
}