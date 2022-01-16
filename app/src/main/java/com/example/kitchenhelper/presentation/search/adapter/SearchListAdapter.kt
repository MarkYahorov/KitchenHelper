package com.example.kitchenhelper.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.databinding.RecipeItemBinding
import com.example.kitchenhelper.presentation.search.model.Recipe

class SearchListAdapter(private val glide: RequestManager) :
    ListAdapter<Recipe, SearchListAdapter.ViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, glide)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.unBind()

        super.onViewRecycled(holder)
    }

    class ViewHolder(private val binding: RecipeItemBinding, private val glide: RequestManager) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                glide.load(recipe.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(recipeItemImage)
                recipeItemTitle.text = recipe.title
            }
        }

        fun unBind() {
            glide.clear(binding.recipeItemImage)
        }
    }

    class SearchDiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}