package com.example.kitchenhelper.presentation.videoItem.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class VideoInfoViewModel @Inject constructor() : ViewModel(), YouTubePlayer.OnInitializedListener {

    val videoFlow = MutableStateFlow<String>("")
    var youtubeId = ""
    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (youtubeId != "") {
            p1?.loadVideo(youtubeId)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("Not yet implemented")
    }
}