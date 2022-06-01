package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.FeedFragmentDirections
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.AppActivityBinding.inflate
import ru.netology.nmedia.databinding.PostCardFragmentBinding
import ru.netology.nmedia.databinding.PostContentFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.FeedFragment

class PostCardFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    private val initialPost by lazy {
        val args by navArgs<PostCardFragmentArgs>()
        args.initialPost
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostCardFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding.postCard) {
            author.text = initialPost?.author
            content.text = initialPost?.content
            published.text = initialPost?.published
            like.isChecked = initialPost!!.likedByMe
            like.text = initialPost?.let { likesSharesDisplay(it.likes) }
            share.text = initialPost?.let { likesSharesDisplay(it.shares) }
            share.isChecked = initialPost!!.shared
            visibility.text = initialPost?.let { likesSharesDisplay(it.views) }
            objectVideo.visibility =
                if (initialPost!!.videoUrl!!.isBlank()) View.GONE else View.VISIBLE
        }

        binding.postCard.menu.setOnClickListener{
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            onMenuRemoveClicked(binding)
                            true
                        }
                        R.id.edit -> {
                            viewModel.onPostEditedFromCard(initialPost!!)
                            viewModel.navigateToPostContentScreenEventFromCard.observe(viewLifecycleOwner) { initialContent ->
                                val direction = FeedFragmentDirections.toPostContentFragment(initialContent)
                                findNavController().navigate(direction)
                            }
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }.root

    private fun onMenuRemoveClicked(binding: PostCardFragmentBinding) {
        if (initialPost != null) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY_CARD, initialPost)
            setFragmentResult(REQUEST_KEY_CARD, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY_CARD = "requestKeyCard"
        const val RESULT_KEY_CARD = "deletedPost"
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
}
