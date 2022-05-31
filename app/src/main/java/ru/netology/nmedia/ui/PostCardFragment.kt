package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.netology.nmedia.FeedFragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardFragmentBinding
import ru.netology.nmedia.viewModel.PostCardViewModel
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.ui.bind
import ru.netology.nmedia.ui.listen

class PostCardFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    private val args by navArgs<PostCardFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostCardFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val viewModel: PostCardViewModel by viewModels(::requireParentFragment)

        viewModel.data.observe(viewLifecycleOwner) {
            with(viewModel.getPostById(args.postId)) {
                this?.let {
                    binding.postCard.bind(it)
                    binding.postCard.listen(it, viewModel)
                }
            }
        }

        viewModel.playVideoUrl.observe(viewLifecycleOwner) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            startActivity(intent)
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

        viewModel.removePost.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        setFragmentResultListener(
            requestKey = PostContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostContentFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newPostContent)
        }
    }.root
}
