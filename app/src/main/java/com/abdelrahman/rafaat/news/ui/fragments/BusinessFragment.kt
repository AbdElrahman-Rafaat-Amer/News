package com.abdelrahman.rafaat.news.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.databinding.FragmentBusinessBinding
import com.abdelrahman.rafaat.news.model.Repository
import com.abdelrahman.rafaat.news.network.NewsClient
import com.abdelrahman.rafaat.news.ui.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.news.utils.connectInternet
import kotlin.math.round

class BusinessFragment : BaseFragment() {

    private lateinit var binding: FragmentBusinessBinding
    private val adapter = NewsRecyclerAdapter()

    private lateinit var viewModelFactory: MainActivityFactory
    private lateinit var viewModel: MainActivityViewModel

    private var page: Int = 1
    private var pageNumbers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        checkConnection()
        initRecyclerView()
        initViewModel()
        observeViewModel()
        refresh()

        binding.connectionLayout.enableConnection.setOnClickListener {
            connectInternet(requireContext())
        }

    }

    private fun initRecyclerView() {
        binding.businessRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.businessRecyclerview.adapter = adapter
        binding.businessRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (page < pageNumbers && page < 6) {
                        page++
                        Log.i("NetworkIssue", "BusinessFragment initRecyclerView:page------->$page")
                        viewModel.getBusiness(page)
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
        viewModel.business.observe(viewLifecycleOwner) {
            if (it.articles.isEmpty()) {
                binding.businessRecyclerview.visibility = View.GONE
                binding.swipeRefreshLayout.visibility = View.GONE
                adapter.setList(emptyList())
                binding.noDataView.root.visibility = View.VISIBLE
            } else {
                pageNumbers = round(it.totalResults.toDouble() / 100).toInt()
                binding.businessRecyclerview.visibility = View.VISIBLE
                binding.swipeRefreshLayout.visibility = View.VISIBLE
                adapter.setList(it.articles)
                binding.noDataView.root.visibility = View.GONE
            }
            binding.swipeRefreshLayout.isRefreshing = false
            binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
            binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
        }
    }

    private fun refresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.i("NetworkIssue", "BusinessFragment refresh")
            viewModel.getBusiness(1)
            binding.swipeRefreshLayout.isRefreshing = true
        }
        binding.swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.mainColor, null))
    }

    override fun onConnected() {
        super.onConnected()
        Log.i("NetworkIssue", "BusinessFragment onConnected")
        binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerAnimationLayout.shimmerFrameLayout.startShimmer()
        binding.connectionLayout.noInternetAnimation.visibility = View.GONE
        binding.connectionLayout.enableConnection.visibility = View.GONE
        viewModel.getBusiness(page)
    }

    override fun onDisconnected() {
        super.onDisconnected()
        Log.i("NetworkIssue", "BusinessFragment onDisconnected")
        binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
        binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
        binding.connectionLayout.noInternetAnimation.visibility = View.VISIBLE
        binding.connectionLayout.enableConnection.visibility = View.VISIBLE
        binding.swipeRefreshLayout.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
        binding.businessRecyclerview.visibility = View.GONE
    }

}