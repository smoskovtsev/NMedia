package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.favorite.setOnClickListener {
            viewModel.onLikeClicked()
        }

        binding.share.setOnClickListener {
            viewModel.onPostShared()
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        content.text = post.content
        published.text = post.published
        likes.text = likesSharesDisplay(post.likes)
        shares.text = likesSharesDisplay(post.shares)
        views.text = likesSharesDisplay(post.views)

        favorite.setImageResource(getLikeIconResId(post.likedByMe))
        share.setImageResource(getShareIconResId(post.shared))
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

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24 else R.drawable.ic_favorite_border_24

    @DrawableRes
    private fun getShareIconResId(shared: Boolean) =
        if (shared) R.drawable.ic_shared_24 else R.drawable.ic_share_24
}