package com.hesham0_0.movietrainingapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hesham0_0.movietrainingapplication.R
import com.hesham0_0.movietrainingapplication.adapters.MovieItemAdapter
import com.hesham0_0.movietrainingapplication.adapters.OnItemClickListener
import com.hesham0_0.movietrainingapplication.ui.viewModel.SharedViewModel
import com.hesham0_0.movietrainingapplication.databinding.FragmentSeeMoreBinding
import com.hesham0_0.movietrainingapplication.models.MovieList
import com.hesham0_0.movietrainingapplication.models.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeMoreFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentSeeMoreBinding? = null
    private val binding get() = _binding!!
    private var viewModel: SharedViewModel? = null
    private var myAdapter = MovieItemAdapter(this)
    private var page = 1

    lateinit var topRatedResponse: MovieList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val selectedListId = viewModel?.selectedListId?.value
        getData(page, selectedListId!!)
        binding.SeeMoreRecycler.adapter = myAdapter
        binding.SeeMoreRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (newState == 0 && !binding.SeeProgressBar.isVisible) {
                        binding.SeeProgressBar.visibility = View.VISIBLE
                        if (page <= topRatedResponse.total_pages) {
                            page++
                            getData(page, selectedListId)
                        }
                    }
                }
            }
        })
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_seeMoreFragment_to_trendingFragment)
        }
    }

    override fun onItemClick(position: Int, movieItem: MovieItem) {
        viewModel?.selectedProduct?.value = movieItem
        findNavController().navigate(R.id.action_seeMoreFragment_to_detailsFragment2)
    }

    private fun getData(page: Int, selectedListId: Int) {
        when (selectedListId) {
            0 -> {
                binding.PageNameText.text = "Trending Movies"
                val trendingValues = viewModel?.trendingValues?.value
                viewModel?.trendingResult(
                    trendingValues!![0],
                    trendingValues[1], page
                )?.observe(viewLifecycleOwner) { response ->
                    topRatedResponse = response
                    myAdapter.setData(topRatedResponse.results, true)
                }
            }
            1 -> {
                viewModel?.topRatedResult(page)?.observe(viewLifecycleOwner) { response ->
                    topRatedResponse = response
                    myAdapter.setData(topRatedResponse.results, true)
                }
            }
            2 -> {
                binding.PageNameText.text = "Upcoming Movies"
                viewModel?.upcomingResult(page)?.observe(viewLifecycleOwner) { response ->
                    topRatedResponse = response
                    myAdapter.setData(topRatedResponse.results, true)
                }
            }
            else -> {
                binding.PageNameText.text = "popular Movies"
                viewModel?.popularResult(page)?.observe(viewLifecycleOwner) { response ->
                    topRatedResponse = response
                    myAdapter.setData(topRatedResponse.results, true)
                }
            }
        }
        binding.SeeProgressBar.visibility = View.INVISIBLE
    }
}