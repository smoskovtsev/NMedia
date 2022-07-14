package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.FeedFragmentDirections
import ru.netology.nmedia.databinding.PostContentFragmentBinding
import ru.netology.nmedia.viewModel.PostViewModel

class PostContentFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    private val initialContent by lazy {
        val args by navArgs<PostContentFragmentArgs>()
        args.initialContent
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(initialContent)
        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }.root

    private fun onOkButtonClicked(binding: PostContentFragmentBinding) {
        //clearFragmentResult(PostCardFragment.REQUEST_KEY_CARD)
        val text = binding.edit.text
        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "resultKey"
    }
}