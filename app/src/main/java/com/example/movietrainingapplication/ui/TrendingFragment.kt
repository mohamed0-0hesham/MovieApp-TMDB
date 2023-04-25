package com.example.movietrainingapplication.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movietrainingapplication.R
import com.example.movietrainingapplication.adapters.MovieItemAdapter
import com.example.movietrainingapplication.adapters.OnItemClickListener
import com.example.movietrainingapplication.adapters.TrendingMoviesAdapter
import com.example.movietrainingapplication.ui.viewModel.SharedViewModel
import com.example.movietrainingapplication.databinding.FragmentTrendingBinding
import com.example.movietrainingapplication.methods.MethodsClass
import com.example.movietrainingapplication.models.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : Fragment(), OnItemClickListener {
    private var viewModel: SharedViewModel? = null
    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!
    private var trendingAdapter = TrendingMoviesAdapter(this)
    private var myAdapter = MovieItemAdapter(this)
    private var latestAdapter = MovieItemAdapter(this)
    private var popularAdapter = MovieItemAdapter(this)
    private lateinit var mediaType: String
    private lateinit var timeWindow: String
    private var isOnCreate: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        mediaType = "all"
        timeWindow = "week"
        isOnCreate = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        if (isOnCreate!!) {
            onCreateUi()
        }
        uiInit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuBottoms()
        seeMoreBottoms()
    }

    override fun onItemClick(position: Int, movieItem: MovieItem) {
        viewModel?.selectedProduct?.value = movieItem
        findNavController().navigate(R.id.action_trending_to_detailsFragment)
    }

    private fun onCreateUi() {
        viewModel?.apply {
            genre.observe(viewLifecycleOwner) { listGenre -> MethodsClass.addGenreList(listGenre) }
            trendingResult(mediaType, timeWindow, 1).observe(viewLifecycleOwner) {}
            topRatedResult(1).observe(viewLifecycleOwner) {}
            upcomingResult(1).observe(viewLifecycleOwner) {}
            popularResult(1).observe(viewLifecycleOwner) {}
            isOnCreate = false
        }
    }

    private fun uiInit() {
        viewModel?.trendingMovieList?.observe(viewLifecycleOwner) { trendingResult ->
            trendingAdapter.setData(trendingResult, false)
            binding.trendingMovies.adapter = trendingAdapter
        }
        viewModel?.topRatedMovieList?.observe(viewLifecycleOwner) { list ->
            myAdapter.setData(list, false)
            binding.recycleTrend.adapter = myAdapter
        }
        viewModel?.upcomingMovieList?.observe(viewLifecycleOwner) { list ->
            latestAdapter.setData(list, false)
            binding.recycleLatest.adapter = latestAdapter
        }
        viewModel?.popularMovieList?.observe(viewLifecycleOwner) { list ->
            popularAdapter.setData(list, false)
            binding.recyclePopular.adapter = popularAdapter
        }
    }

    private fun seeMoreBottoms() {
        binding.seeTrendingBtn.setOnClickListener {
            viewModel?.trendingValues?.value = listOf(mediaType, timeWindow)
            viewModel?.selectedListId?.value = 0
            findNavController().navigate(R.id.action_trendingFragment_to_seeMoreFragment)
        }
        binding.seeMoreRatedBtn.setOnClickListener {
            viewModel?.selectedListId?.value = 1
            findNavController().navigate(R.id.action_trendingFragment_to_seeMoreFragment)
        }
        binding.seeMoreLatestBtn.setOnClickListener {
            viewModel?.selectedListId?.value = 2
            findNavController().navigate(R.id.action_trendingFragment_to_seeMoreFragment)
        }
        binding.seeMorePopularBtn.setOnClickListener {
            viewModel?.selectedListId?.value = 3
            findNavController().navigate(R.id.action_trendingFragment_to_seeMoreFragment)
        }
    }

    private fun menuBottoms() {
        binding.trendingBtn.setOnClickListener {
            if (binding.trendingMenu.isVisible) {

                binding.trendingMenu.visibility = View.GONE
            } else {
                binding.trendingMenu.visibility = View.VISIBLE
            }
        }
        binding.radioMediaGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.trendingMenuAll -> {
                    mediaType = "all"
                }
                R.id.trendingMenuMovie -> {
                    mediaType = "movie"
                }
                R.id.trendingMenuTV -> {
                    mediaType = "tv"
                }
            }
            viewModel!!.trendingResult(mediaType, timeWindow, 1).observe(viewLifecycleOwner) {}
        }
        binding.radioTimeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.trendingMenuDay -> {
                    timeWindow = "day"
                }
                R.id.trendingMenuWeek -> {
                    timeWindow = "week"
                }
            }
            viewModel!!.trendingResult(mediaType, timeWindow, 1).observe(viewLifecycleOwner) {}
        }
    }
}