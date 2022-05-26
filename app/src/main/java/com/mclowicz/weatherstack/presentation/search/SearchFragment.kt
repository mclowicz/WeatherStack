package com.mclowicz.weatherstack.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mclowicz.weatherstack.databinding.FragmentSearchBinding
import com.mclowicz.weatherstack.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.title = "Search"
        bindUI()
        initObservers()
    }

    private fun bindUI() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                validateQuery(query = query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do nothing
                return false
            }

        })
    }

    private fun initObservers() {
        with(viewModel) {
            searchEvent.observeEvent(viewLifecycleOwner) { searchEvent ->
                val query = (searchEvent as? SearchEvent.NavigateToDetailScreen)?.searchQuery
                query?.let { query ->
                    SearchFragmentDirections
                        .actionSearchFragmentToDetailFragment(searchQuery = query)
                        .also { findNavController().navigate(it) }
                }
            }
        }
    }

    private fun validateQuery(query: String?) {
        when {
            query.isNullOrBlank() -> {
                showToast(message = "City name cannot be blank.")
            }
            query.length < 3 -> {
                showToast(message = "City name needs at least 3 characters.")
            }
            query.isDigitsOnly() -> {
                showToast(message = "City name cannot be a number123.")
            }
            else -> {
                viewModel.submitQuery(query = query)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}