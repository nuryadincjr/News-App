package com.dna.newsapp.ui.screen.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.data.repository.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ActivityUiState {

    val isLoading: Boolean
    val errorMessages: String

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessages: String,
    ) : ActivityUiState

    data class HasNews(
        val newsEntity: List<NewsEntity>?,
        override val isLoading: Boolean,
        override val errorMessages: String,
    ) : ActivityUiState
}

private data class ActivityViewModelState(
    val newsResponse: List<NewsEntity>? = null,
    val isLoading: Boolean = false,
    val errorMessages: String = "",
) {

    fun toNewsUiState(): ActivityUiState =
        if (newsResponse == null) {
            ActivityUiState.NoData(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            ActivityUiState.HasNews(
                newsEntity = newsResponse,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        ActivityViewModelState(
            isLoading = true,
        )
    )

    val activityUiState = viewModelState
        .map(ActivityViewModelState::toNewsUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toNewsUiState()
        )

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            newsRepository.getNews().collect { result ->
                viewModelState.update {
                    it.copy(
                        newsResponse = result,
                        isLoading = false,
                        errorMessages = ""
                    )
                }
            }
        }
    }

    fun deleteNews(id: String) {
        viewModelScope.launch {
            newsRepository.deleteNews(id)
        }
    }
}
