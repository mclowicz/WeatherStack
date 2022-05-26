package com.mclowicz.weatherstack.presentation.search

sealed class SearchEvent {
    class NavigateToDetailScreen(val searchQuery: String): SearchEvent()
}