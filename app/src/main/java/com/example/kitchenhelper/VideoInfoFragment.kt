package com.example.kitchenhelper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.databinding.FragmentVideoInfoBinding
import com.example.kitchenhelper.presentation.videoItem.di.VideoItemComponent
import com.example.kitchenhelper.presentation.videoItem.viewModel.VideoInfoViewModel
import com.google.android.youtube.player.YouTubePlayerSupportFragmentXKt


class VideoInfoFragment : YouTubePlayerSupportFragmentXKt() {

    private val args by navArgs<VideoInfoFragmentArgs>()
    private lateinit var videoInfoViewBinding: FragmentVideoInfoBinding
    private lateinit var videoInfoViewModel: VideoInfoViewModel
    private val videoInfoComponent by lazy {
        VideoItemComponent.create(App.appComponent, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.e("TAG", "")
    }

    override fun onCreate(var1: Bundle?) {
        super.onCreate(var1)

        videoInfoViewModel = createViewModel(this, videoInfoComponent.videoItemViewModel)
        videoInfoViewModel.youtubeId = args.youTubeId
    }

    override fun onCreateView(
        var1: LayoutInflater, var2: ViewGroup?,
        var3: Bundle?
    ): View {
        videoInfoViewBinding = FragmentVideoInfoBinding.inflate(var1, var2, false)
        return videoInfoViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        videoInfoViewBinding.videoInfoVideoPlayer.initialize(
            "AIzaSyA2pp4kgcGXUr4f9qT-YNgn5sv-cfOc2lA",
            videoInfoViewModel
        )
    }
}