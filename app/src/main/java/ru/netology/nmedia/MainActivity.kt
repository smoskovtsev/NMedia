package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1L,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36"
         )

        binding.render(post)

        binding.favorite.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likes = post.likes + 1 else post.likes = post.likes - 1
            binding.favorite.setImageResource(getLikeIconResId(post.likedByMe))
            binding.likes.text = likesSharesDisplay(post.likes)
        }

        binding.share.setOnClickListener {
            post.shared = true
            post.shares = post.shares + 1
            binding.share.setImageResource(getShareIconResId(post.shared))
            binding.shares.text = likesSharesDisplay(post.shares)
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