package com.example.kitchenhelper.presentation.searchVideo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.kitchenhelper.presentation.searchVideo.mappers.toPresentation
import com.example.kitchenhelper.presentation.searchVideo.models.Video
import com.example.kitchenhelper.presentation.searchVideo.models.VideoRequestParams
import com.example.kitchenhelper.presentation.searchVideo.repository.SearchVideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchVideoViewModel @Inject constructor(
    private val repository: SearchVideoRepository,
    private val request: VideoRequestParams
) : ViewModel() {

    val videoFlow = MutableStateFlow<PagingData<Video>?>(null)
    val errorFlow = MutableSharedFlow<Throwable>()

    fun searchVideos(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query!= null && query.isNotBlank()) {
                request.query = query
                repository.searchVideo()
                    .map { paging -> paging.map { it.toPresentation() } }
                    .cachedIn(viewModelScope)
                    .catch { error ->
                        errorFlow.emit(error)
                    }
                    .collect { videoFlow.emit(it) }
            } else {
                errorFlow.emit(Throwable("Empty String"))
            }
        }
    }
}