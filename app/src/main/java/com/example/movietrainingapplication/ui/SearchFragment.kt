package com.example.movietrainingapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movietrainingapplication.R
import com.example.movietrainingapplication.adapters.MovieItemAdapter
import com.example.movietrainingapplication.adapters.OnItemClickListener
import com.example.movietrainingapplication.ui.viewModel.SharedViewModel
import com.example.movietrainingapplication.databinding.FragmentSearchBinding
import com.example.movietrainingapplication.models.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), OnItemClickListener {
    lateinit var viewModel: SharedViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var myAdapter = MovieItemAdapter(this)
    private lateinit var searchMovie: String
    private lateinit var movieList: List<MovieItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        uiInit()
        return _binding!!.root
    }

    private fun uiInit() {
        binding.SearchText.setText(viewModel.searchMovieName.value)
        binding.SearchRecycle.adapter = myAdapter
        viewModel.searchMovieList.observe(viewLifecycleOwner) {
            movieList = it
            myAdapter.setData(movieList, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SearchRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (newState == 0) {
                        binding.searchProgressbar.visibility = View.VISIBLE
                        viewRecycler()
                    }
                }
            }
        })

        binding.SearchText.doOnTextChanged { text, start, before, count ->
            if (text.toString() != viewModel.searchMovieName.value) {
                binding.textView2.visibility = View.GONE
                viewModel.searchMovieList.value = emptyList()
                viewModel.searchMovieName.value = text.toString().trimEnd()
                viewModel.selectedPageSearch.value = 1
                viewRecycler()
            }
        }
    }

    fun viewRecycler() {
        viewModel.searchResult().observe(viewLifecycleOwner) { taskCode ->
            when (taskCode) {
                "Successful" -> binding.searchProgressbar.visibility = View.GONE
                "No More Results" -> Toast.makeText(context, "No More Results", Toast.LENGTH_LONG)
                    .show()
                "No Data" -> binding.textView2.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemClick(position: Int, movieItem: MovieItem) {
        viewModel.selectedProduct.value = movieList[position]
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment2)
    }
}