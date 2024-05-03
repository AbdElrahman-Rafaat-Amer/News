package com.abdelrahman.rafaat.news.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.databinding.FragmentHomeBinding
import com.abdelrahman.rafaat.news.localdatabase.LocalSource
import com.abdelrahman.rafaat.news.model.Article
import com.abdelrahman.rafaat.news.model.Repository
import com.abdelrahman.rafaat.news.network.NewsClient
import com.abdelrahman.rafaat.news.ui.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.news.ui.readnews.ReadNewsActivity
import com.abdelrahman.rafaat.news.utils.connectInternet
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = NewsRecyclerAdapter(onItemClicked = { url ->
        handleOnItemClicked(url)
    }, onItemLongClicked = { article ->
        handleOnItemLongClicked(article)
    })

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

        initRecyclerView()
        initViewModel()
        observeViewModel()
        refresh()
        handleSearchView()

        binding.connectionLayout.enableConnection.setOnClickListener {
            connectInternet(requireContext())
        }

    }

    private fun initRecyclerView() {
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecyclerview.adapter = adapter

        binding.homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (page < pageNumbers && page < 6) {
                        page++
                        Log.i("NetworkIssue", "HomeFragment onScrollStateChanged: page---->${page}")
                        viewModel.getNews(page)
                    }
                }
            }
        })
    }

    private fun initViewModel() {
        viewModelFactory = MainActivityFactory(
            Repository.getNewsClient(
                NewsClient.getNewsClient(),
                LocalSource.getInstance(requireContext()),
                requireActivity().application
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
            Log.i("NetworkIssue", "HomeFragment refresh:")
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
                Log.i("NetworkIssue", "HomeFragment onQueryTextChange:newText----->$newText")
                job?.cancel()
                if (isInternetConnected) {
                    job = MainScope().launch {
                        delay(500L)
                        if (newText!!.isNotEmpty()) {
                            viewModel.getNewsBySearch(page, newText)
                        } else {
                            Log.i("NetworkIssue", "HomeFragment onQueryTextChange:")
                            viewModel.getNews(page)
                        }
                    }
                }

                return false
            }

        })
    }

    private fun handleOnItemLongClicked(article: Article) {
        viewModel.saveNews(article)
    }

    private fun handleOnItemClicked(url: String) {
        val intent = Intent(context, ReadNewsActivity::class.java)
        intent.putExtra("NEWS_URL", url)
        context?.startActivity(intent)
    }

    override fun onConnected() {
        super.onConnected()
        Log.i("NetworkIssue", "HomeFragment onConnected: ")
        binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerAnimationLayout.shimmerFrameLayout.startShimmer()
        binding.connectionLayout.noInternetAnimation.visibility = View.GONE
        binding.connectionLayout.enableConnection.visibility = View.GONE
        viewModel.getNews(page)
    }

    override fun onDisconnected() {
        super.onDisconnected()
        Log.i("NetworkIssue", "HomeFragment onDisconnected: ")
        binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
        binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
        binding.connectionLayout.noInternetAnimation.visibility = View.VISIBLE
        binding.connectionLayout.enableConnection.visibility = View.VISIBLE
        binding.swipeRefreshLayout.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
        binding.homeRecyclerview.visibility = View.GONE
        binding.searchView.visibility = View.GONE
    }
}