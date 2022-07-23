package com.abdelrahman.rafaat.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatButton

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.myapplication.model.Repository
import com.abdelrahman.rafaat.myapplication.network.NewsClient
import com.abdelrahman.rafaat.myapplication.utils.ConnectionLiveData
import com.abdelrahman.rafaat.myapplication.utils.connectInternet
import com.airbnb.lottie.LottieAnimationView
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    @BindView(R.id.search_view)
    lateinit var searchView: SearchView

    @BindView(R.id.home_recyclerview)
    lateinit var homeRecyclerview: RecyclerView

    @BindView(R.id.swipe_refresh_layout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.shimmer_frame_layout)
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    @BindView(R.id.no_internet_animation)
    lateinit var noInternetAnimation: LottieAnimationView

    @BindView(R.id.enable_connection)
    lateinit var enableConnection: AppCompatButton

    @BindView(R.id.no_data_view)
    lateinit var noDataView: View

    private val adapter = NewsRecyclerAdapter()

    private lateinit var viewModelFactory: MainActivityFactory
    private lateinit var viewModel: MainActivityViewModel

    private var page: Int = 1
    private var pageNumbers: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnection()
        initRecyclerView()
        initViewModel()
        observeViewModel()
        refresh()
        handleSearchView()

        enableConnection.setOnClickListener {
            Log.i(TAG, "onViewCreated: enableConnection----------> ")
            connectInternet(requireContext())
        }

    }

    private fun checkConnection() {
        ConnectionLiveData.getInstance(requireContext()).observe(viewLifecycleOwner) {
            if (it) {
                shimmerFrameLayout.visibility = View.VISIBLE
                shimmerFrameLayout.startShimmerAnimation()
                noInternetAnimation.visibility = View.GONE
                enableConnection.visibility = View.GONE
                viewModel.getNews(page)
            } else {
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
                noInternetAnimation.visibility = View.VISIBLE
                enableConnection.visibility = View.VISIBLE
                swipeRefreshLayout.visibility = View.GONE
                searchView.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        homeRecyclerview.adapter = adapter

        homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                homeRecyclerview.visibility = View.GONE
                adapter.setList(emptyList())
                noDataView.visibility = View.VISIBLE
            } else {
                pageNumbers = round(it.totalResults.toDouble() / 100).toInt()
                Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                homeRecyclerview.visibility = View.VISIBLE
                adapter.setList(it.articles)
                noDataView.visibility = View.GONE
            }
            swipeRefreshLayout.visibility = View.VISIBLE
            searchView.visibility = View.VISIBLE
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmerAnimation()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getNews(1)
            swipeRefreshLayout.isRefreshing = true
        }
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.mainColor, null))
    }

    private fun handleSearchView() {
        var job: Job? = null

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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