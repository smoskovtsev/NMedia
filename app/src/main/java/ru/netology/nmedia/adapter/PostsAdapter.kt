package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: PostBinding, listener: PostInteractionListener) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
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

        init {
            binding.favorite.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onPostShared(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                likes.text = likesSharesDisplay(post.likes)
                shares.text = likesSharesDisplay(post.shares)
                views.text = likesSharesDisplay(post.views)

                favorite.setImageResource(getLikeIconResId(post.likedByMe))
                share.setImageResource(getShareIconResId(post.shared))
                menu.setOnClickListener{popupMenu.show()}
            }
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

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}