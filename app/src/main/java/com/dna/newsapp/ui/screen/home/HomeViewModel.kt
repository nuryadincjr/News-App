package com.dna.newsapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dna.newsapp.data.local.entity.NewsEntity
import com.dna.newsapp.data.repository.news.NewsRepository
import com.dna.newsapp.model.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: String

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessages: String,
    ) : HomeUiState

    data class HasNews(
        val newsResponse: NewsResponse,
        override val isLoading: Boolean,
        override val errorMessages: String,
    ) : HomeUiState
}

private data class HomeViewModelState(
    val newsResponse: NewsResponse? = null,
    val isLoading: Boolean = false,
    val errorMessages: String = "",
) {

    fun toNewsUiState(): HomeUiState =
        if (newsResponse == null) {
            HomeUiState.NoData(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            HomeUiState.HasNews(
                newsResponse = newsResponse,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
        )
    )

    val homeUiState = viewModelState
        .map(HomeViewModelState::toNewsUiState)
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
            val locales = Locale.getDefault().country
            newsRepository.getNews(locales).collect { result ->
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

    fun insertNews(entities: NewsEntity) {
        viewModelScope.launch {
            newsRepository.insertNews(entities)
        }
    }

    val filterList = newsRepository.getSortFilter()
}
