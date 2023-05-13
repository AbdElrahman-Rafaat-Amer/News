package com.abdelrahman.rafaat.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.databinding.FragmentEntertainmentBinding
import com.abdelrahman.rafaat.myapplication.model.Repository
import com.abdelrahman.rafaat.myapplication.network.NewsClient
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.myapplication.utils.ConnectionLiveData
import com.abdelrahman.rafaat.myapplication.utils.connectInternet
import kotlin.math.round

private const val TAG = "EntertainmentFragment"

class EntertainmentFragment : Fragment() {

    private lateinit var binding: FragmentEntertainmentBinding
    private val adapter = NewsRecyclerAdapter()

    private lateinit var viewModelFactory: MainActivityFactory
    private lateinit var viewModel: MainActivityViewModel

    private var page: Int = 1
    private var pageNumbers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntertainmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnection()
        initRecyclerView()
        initViewModel()
        observeViewModel()
        refresh()

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
                viewModel.getEntertainment(page)
            } else {
                binding.shimmerAnimationLayout.shimmerFrameLayout.visibility = View.GONE
                binding.shimmerAnimationLayout.shimmerFrameLayout.stopShimmer()
                binding.connectionLayout.noInternetAnimation.visibility = View.VISIBLE
                binding.connectionLayout.enableConnection.visibility = View.VISIBLE
                binding.swipeRefreshLayout.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        binding.entertainmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.entertainmentRecyclerview.adapter = adapter
        binding.entertainmentRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.i(TAG, "onScrollStateChanged: reach end of recyclerView")
                    Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                    Log.i(TAG, "observeViewModel: page------------------------> $page")
                    if (page < pageNumbers && page < 6) {
                        page++
                        viewModel.getEntertainment(page)
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
        viewModel.entertainment.observe(viewLifecycleOwner) {
            if (it.articles.isEmpty()) {
                binding.entertainmentRecyclerview.visibility = View.GONE
                binding.swipeRefreshLayout.visibility = View.GONE
                adapter.setList(emptyList())
                binding.noDataView.root.visibility = View.VISIBLE
            } else {
                pageNumbers = round(it.totalResults.toDouble() / 100).toInt()
                Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                binding.entertainmentRecyclerview.visibility = View.VISIBLE
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
            viewModel.getEntertainment(1)
            binding.swipeRefreshLayout.isRefreshing = true
        }
        binding.swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.mainColor, null))
    }
}