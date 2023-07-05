package com.dna.newsapp.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.newsapp.data.repository.news.NewsRepository
import com.dna.newsapp.model.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {

    val isLoading: Boolean
    val errorMessages: String
    val searchInput: String

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessages: String,
        override val searchInput: String
    ) : SearchUiState

    data class HasNews(
        val newsResponse: NewsResponse,
        override val isLoading: Boolean,
        override val errorMessages: String,
        override val searchInput: String
    ) : SearchUiState
}

private data class SearchViewModelState(
    val newsResponse: NewsResponse? = null,
    val isLoading: Boolean = false,
    val errorMessages: String = "",
    val searchInput: String = "",
) {

    fun toNewsUiState(): SearchUiState =
        if (newsResponse == null) {
            SearchUiState.NoData(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            SearchUiState.HasNews(
                newsResponse = newsResponse,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        SearchViewModelState(
            isLoading = false,
        )
    )

    val searchUiState = viewModelState
        .map(SearchViewModelState::toNewsUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toNewsUiState()
        )

    fun refreshData(query: String, sort: String, from: String) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            newsRepository.getNews(q = query, sortBy = sort, from = from).collect { result ->
                result.onSuccess { newsResponse ->
                    viewModelState.update {
                        it.copy(
                            newsResponse = newsResponse,
                            isLoading = false,
                            errorMessages = ""
                        )
                    }
                }
                result.onFailure { throwable ->
                    viewModelState.update {
                        it.copy(
                            newsResponse = null,
                            isLoading = false,
                            errorMessages = throwable.message.toString()
                        )
                    }
                }
            }
        }
    }

    val sortDefault = newsRepository.getSortDefault()

    val filterList = newsRepository.getSortFilter()
}
