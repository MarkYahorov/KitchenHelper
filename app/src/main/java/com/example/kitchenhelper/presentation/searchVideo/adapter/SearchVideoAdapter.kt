package com.example.kitchenhelper.presentation.searchVideo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.databinding.SearchVideoItemBinding
import com.example.kitchenhelper.presentation.searchVideo.models.Video

class SearchVideoAdapter(private val glide: RequestManager, private val onClick: (Video) -> Unit) :
    PagingDataAdapter<Video, SearchVideoAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SearchVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, glide, onClick)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.unBind()

        super.onViewRecycled(holder)
    }

    class ViewHolder(
        private val viewBinding: SearchVideoItemBinding,
        private val glide: RequestManager,
        private val onClick: (Video) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(video: Video) {
            with(viewBinding) {
                glide.load(video.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(searchVideoItemImage)
                searchVideoItemTitle.text = video.title
                searchVideoItemRating.text = video.rating.toString()
                root.setOnClickListener { onClick(video) }
            }
        }

        fun unBind() {
            glide.clear(viewBinding.searchVideoItemImage)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video) =
            oldItem.youTubeId == newItem.youTubeId

        override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
    }
}