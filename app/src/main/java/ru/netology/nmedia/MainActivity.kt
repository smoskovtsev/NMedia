package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel::onLikeClicked, viewModel::onPostShared)
        binding.postsRecycleView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }

//    private fun ActivityMainBinding.render(posts: List<Post>) {
//        for (post in posts) {
//            PostBinding.inflate(
//                layoutInflater, root, true
//            ).render(post)
//        }
//    }

//    private fun PostBinding.render(post: Post) {
//        author.text = post.author
//        content.text = post.content
//        published.text = post.published
//        likes.text = likesSharesDisplay(post.likes)
//        shares.text = likesSharesDisplay(post.shares)
//        views.text = likesSharesDisplay(post.views)
//
//        favorite.setOnClickListener { viewModel.onLikeClicked(post) }
//        share.setOnClickListener { viewModel.onPostShared(post) }
//
//        favorite.setImageResource(getLikeIconResId(post.likedByMe))
//        share.setImageResource(getShareIconResId(post.shared))
//    }

//    private fun likesSharesDisplay (amount: Int): String {
//        return when {
//            (amount < 1000) -> "$amount"
//            (amount < 10000) && (amount % 1000) < 100 -> "${amount/1000}K"
//            (amount < 10000) -> "${amount/1000}.${(amount % 1000)/100}K"
//            (amount < 1_000_000) -> "${amount/1000}K"
//            (amount == 1_000_000) -> "${amount/1_000_000}M"
//            (amount > 1_000_000) && (amount % 1_000_000) < 100_000 -> "${amount/1_000_000}M"
//            else -> "${amount/1_000_000}.${(amount % 1_000_000)/100_000}M"
//        }
//    }

}