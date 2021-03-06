package com.abdelrahman.rafaat.myapplication.business

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.mainscreen.view.NewsRecyclerAdapter
import com.abdelrahman.rafaat.myapplication.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.myapplication.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.myapplication.model.Repository
import com.abdelrahman.rafaat.myapplication.network.NewsClient
import com.abdelrahman.rafaat.myapplication.utils.ConnectionLiveData
import com.abdelrahman.rafaat.myapplication.utils.connectInternet
import com.airbnb.lottie.LottieAnimationView
import com.facebook.shimmer.ShimmerFrameLayout
import kotlin.math.round

private const val TAG = "BusinessFragment"

class BusinessFragment : Fragment() {

    @BindView(R.id.business_recyclerview)
    lateinit var businessRecyclerview: RecyclerView

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
        val view = inflater.inflate(R.layout.fragment_business, container, false)
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

        enableConnection.setOnClickListener {
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
                viewModel.getBusiness(page)
            } else {
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
                noInternetAnimation.visibility = View.VISIBLE
                enableConnection.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        businessRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        businessRecyclerview.adapter = adapter
        businessRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.i(TAG, "onScrollStateChanged: reach end of recyclerView")
                    Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                    Log.i(TAG, "observeViewModel: page------------------------> $page")
                    if (page < pageNumbers && page < 6) {
                        page++
                        viewModel.getBusiness(page)
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
                NewsClient.getNewsClient(),
                PreferenceManager.getDefaultSharedPreferences(requireActivity())
            )
        )

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[MainActivityViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.business.observe(viewLifecycleOwner) {
            if (it.articles.isEmpty()) {
                businessRecyclerview.visibility = View.GONE
                swipeRefreshLayout.visibility = View.GONE
                adapter.setList(emptyList())
                noDataView.visibility = View.VISIBLE
            } else {
                pageNumbers = round(it.totalResults.toDouble() / 100).toInt()
                Log.i(TAG, "observeViewModel: pageNumbers-----------------> $pageNumbers")
                businessRecyclerview.visibility = View.VISIBLE
                swipeRefreshLayout.visibility = View.VISIBLE
                adapter.setList(it.articles)
                noDataView.visibility = View.GONE
            }
            swipeRefreshLayout.isRefreshing = false
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmerAnimation()
        }
    }

    private fun refresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getBusiness(1)
            swipeRefreshLayout.isRefreshing = true
        }
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.mainColor, null))
    }

}