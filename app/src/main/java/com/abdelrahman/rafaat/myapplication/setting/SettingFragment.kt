package com.abdelrahman.rafaat.myapplication.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import butterknife.BindView
import butterknife.ButterKnife
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.model.Repository
import com.abdelrahman.rafaat.myapplication.network.NewsClient
import com.abdelrahman.rafaat.myapplication.setting.viewmodel.SettingFactory
import com.abdelrahman.rafaat.myapplication.setting.viewmodel.SettingViewModel

private const val TAG = "SettingFragment"

class SettingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    @BindView(R.id.country_spinner)
    lateinit var countrySpinner: Spinner

    @BindView(R.id.sortBy_spinner)
    lateinit var sortBySpinner: Spinner

    private lateinit var countries: Array<String>
    private lateinit var sortByArray: Array<String>
    private lateinit var countriesValues: Array<String>

    private lateinit var country: String
    private lateinit var sortBy: String


    private lateinit var viewModelFactory: SettingFactory
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initCountrySpinner()

        initSortBySpinner()
    }

    private fun initViewModel() {
        viewModelFactory = SettingFactory(
            Repository.getNewsClient(
                NewsClient.getNewsClient(),
                PreferenceManager.getDefaultSharedPreferences(requireActivity())
            )
        )

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[SettingViewModel::class.java]
    }

    private fun initCountrySpinner() {
        countries = resources.getStringArray(R.array.country_values)
        countriesValues = resources.getStringArray(R.array.language_values)
        countrySpinner.onItemSelectedListener = this
        val country =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
        country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = country
    }

    private fun initSortBySpinner() {
        sortByArray = resources.getStringArray(R.array.sortBy_values)
        sortBySpinner.onItemSelectedListener = this

        val sortBy =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortByArray)
        sortBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortBySpinner.adapter = sortBy
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent?.id) {
            R.id.country_spinner -> {
                country = countriesValues[position]
                Log.i(TAG, "onItemSelected: country----------------> $country")
            }
            R.id.sortBy_spinner -> {
                sortBy = sortByArray[position]
                Log.i(TAG, "onItemSelected: sortBy-----------------> $sortBy")
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onStop() {
        super.onStop()
        viewModel.updateSharedPreference(country, sortBy)
    }

}