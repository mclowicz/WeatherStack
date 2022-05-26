package com.mclowicz.weatherstack.presentation.search

import androidx.lifecycle.ViewModel
import com.mclowicz.weatherstack.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(

) : ViewModel() {

    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun submitQuery(query: String) {
        searchEvent.value = SearchEvent.NavigateToDetailScreen(searchQuery = query)
    }
}