package com.mclowicz.weatherstack.presentation.search

import com.mclowicz.weatherstack.presentation.base.BaseVM
import com.mclowicz.weatherstack.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(

) : BaseVM() {

    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun submitQuery(query: String) {
        searchEvent.value = SearchEvent.NavigateToDetailScreen(searchQuery = query)
    }
}