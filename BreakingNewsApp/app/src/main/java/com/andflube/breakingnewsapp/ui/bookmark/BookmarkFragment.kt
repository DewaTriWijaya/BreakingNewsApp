package com.andflube.breakingnewsapp.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.andflube.breakingnewsapp.databinding.FragmentBookmarkBinding
import com.andflube.breakingnewsapp.ui.ViewModelFactory
import com.andflube.breakingnewsapp.ui.home.HomeAdapter
import com.andflube.breakingnewsapp.ui.home.HomeViewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        val newsAdapter = HomeAdapter { news ->
            if (news.isBookmarked){
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        viewModel.getBookmarked().observe(viewLifecycleOwner) { bookmarkedNews ->
            newsAdapter.submitList(bookmarkedNews)
        }

        binding?.recyclerview?.apply {
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}