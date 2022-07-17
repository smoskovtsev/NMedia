package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.*
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

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val initialPost by lazy {
        val args by navArgs<PostCardFragmentArgs>()
        args.initialPost
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostCardFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
//
        val viewHolder = PostsAdapter.ViewHolder(binding.postCard, viewModel)
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == initialPost?.id } ?: run {
                findNavController().navigateUp() // the post was deleted, close the fragment
                return@observe
            }
            viewHolder.bind(post)
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideoUrl.observe(viewLifecycleOwner) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            startActivity(intent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
            val direction = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }

    }.root

    override fun onResume () {
        super.onResume()

        setFragmentResultListener(PostContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostContentFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }
    }
}
