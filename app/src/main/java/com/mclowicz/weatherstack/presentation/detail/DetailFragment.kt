package com.mclowicz.weatherstack.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mclowicz.weatherstack.databinding.FragmentDetailBinding
import com.mclowicz.weatherstack.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailVM by viewModels()
    private val arguments: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.title = "Detail"

        val query = arguments.searchQuery
        viewModel.searchQuery(query = query)
        initObservers()
    }


    private fun initObservers() {
        with (viewModel) {
            viewState.observe(viewLifecycleOwner) { viewState ->
                updateUI(viewState)
            }
        }
    }

    private fun updateUI(viewState: DetailViewState) {
        when (viewState) {
            is  DetailViewState.Loading -> {
                Log.d("Weatherstack", "DetailFragment: loading")
                binding.apply {
                    progressBar.isVisible = true
                    contentLayout.isVisible = false
                }
            }
            is DetailViewState.Error -> {
                Log.d("Weatherstack", "DetailFragment: error")
                binding.apply {
                    progressBar.isVisible = false
                    errorLayout.isVisible = true
                    contentLayout.isVisible = false
                    errorLayout.text = viewState.message
                }
            }
            is DetailViewState.Content -> {
                Log.d("Weatherstack", "DetailFragment: content")
                binding.apply {
                    progressBar.isVisible = false
                    errorLayout.isVisible = false
                    contentLayout.isVisible = true

                    textCityName.text = "City: ${viewState.data.cityName}"
                    textTemperature.text = "Temp: ${viewState.data.temperature}"
                    textWindSpeed.text = "Wind speed: ${viewState.data.windSpeed}"
                    textPressure.text = "Pressure: ${viewState.data.pressure}"
                    textHumidity.text = "Humidity: ${viewState.data.humidity}"
                }
            }
        }
    }
}