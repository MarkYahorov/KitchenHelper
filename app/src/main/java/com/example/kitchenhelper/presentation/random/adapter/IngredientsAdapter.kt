package com.example.kitchenhelper.presentation.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.R
import com.example.kitchenhelper.databinding.IngridientsListItemBinding
import com.example.kitchenhelper.presentation.random.model.Ingredients

class IngredientsAdapter(private val glide: RequestManager) :
    ListAdapter<Ingredients, IngredientsAdapter.IngredientsViewHolder>(DiffCallback()) {

    companion object {
        private val DEFAULT_PATH_TO_IMAGE = "https://spoonacular.com/cdn/ingredients_100x100/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val itemBinding =
            IngridientsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(itemBinding, glide)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: IngredientsViewHolder) {
        holder.unBind()

        super.onViewRecycled(holder)
    }

    class IngredientsViewHolder(
        private val binding: IngridientsListItemBinding,
        private val glide: RequestManager
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: Ingredients) {
            with(binding) {
                glide.load(DEFAULT_PATH_TO_IMAGE + ingredients.image).diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).placeholder(R.drawable.food_ic)
                    .circleCrop()
                    .into(ingredientImage)
                ingredientsTitle.text = ingredients.originalString
            }
        }

        fun unBind() {
            glide.clear(binding.ingredientImage)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ingredients>() {
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients) =
            oldItem == newItem
    }
}