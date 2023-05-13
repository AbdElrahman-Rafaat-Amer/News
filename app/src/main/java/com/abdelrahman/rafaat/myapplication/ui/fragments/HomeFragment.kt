package com.abdelrahman.rafaat.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.databinding.FragmentHomeBinding
import com.abdelrahman.rafaat.myapplication.model.Repository
import com.abdelrahman.rafaat.myapplication.network.NewsClient
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.myapplication.utils.ConnectionLiveData
import com.abdelrahman.rafaat.myapplication.utils.connectInternet
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = NewsRecyclerAdapter()

    private lateinit var viewModelFactory: MainActivityFactory
    private lateinit var viewModel: MainActivityViewModel

    private var page: Int = 1
    private var pageNumbers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnection()
        initRecyclerView()
        initViewModel()
        observeViewModel()
        refresh()
        handleSearchView()

        binding.connectionLayout.enableConnection.setOnClickListener {
            connectInternet(requireContext())
        }

    }

    private fun checkConnection() {
        ConnectionLiveData.getInstance(requireContext()).observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.VISIBLE
                binding.shimmerAnimationLayout.shimmerFrameLayout.startShimmer()
                binding.connectionLayout.noInternetAnimation.visibility = View.GONE
                binding.connectionLayout.enableConnection.visibility = View.GONE
                viewModel.getNews(page)
            } else {
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
                binding.connectionLayout.noInternetAnimation.visibility = View.VISIBLE
                binding.connectionLayout.enableConnection.visibility = View.VISIBLE
                binding.swipeRefreshLayout.visibility = View.GONE
                binding.searchView.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
         binding.homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
         binding.homeRecyclerview.adapter = adapter

         binding.homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.i(TAG, "onScrollStateChanged: reach end of recyclerView")
                    Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                    Log.i(TAG, "observeViewModel: page------------------------> $page")
                    if (page < pageNumbers && page < 6) {
                        page++
                        viewModel.getNews(page)
                    } else {
                        Log.i(TAG, "onScrollStateChanged: page--------------> ")
                    }
                }
            }
        })
    }

    private fun initViewModel() {
        viewModelFactory = MainActivityFactory(
            Repository.getNewsClient(
                NewsClient.getNewsClient(), requireActivity().application
            ), requireActivity().application
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[MainActivityViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner) {
            if (it.articles.isEmpty()) {
                 binding.homeRecyclerview.visibility = View.GONE
                adapter.setList(emptyList())
                 binding.noDataView.root.visibility = View.VISIBLE
            } else {
                pageNumbers = round(it.totalResults.toDouble() / 100).toInt()
                Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                 binding.homeRecyclerview.visibility = View.VISIBLE
                adapter.setList(it.articles)
                 binding.noDataView.root.visibility = View.GONE
            }
            binding.swipeRefreshLayout.visibility = View.VISIBLE
            binding.searchView.visibility = View.VISIBLE
            binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
            binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getNews(1)
            binding.swipeRefreshLayout.isRefreshing = true
        }
        binding.swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.mainColor, null))
    }

    private fun handleSearchView() {
        var job: Job? = null

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    if (newText!!.isNotEmpty()) {
                        viewModel.getNewsBySearch(
                            page, newText
                        )
                    } else {
                        viewModel.getNews(page)
                    }
                }
                return false
            }

        })
    }
}