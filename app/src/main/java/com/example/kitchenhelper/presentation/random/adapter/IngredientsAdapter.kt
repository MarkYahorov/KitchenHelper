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
import com.example.kitchenhelper.presentation.random.model.Ingredient

class IngredientsAdapter(
    private val glide: RequestManager,
    private val holderClicked: (Ingredient) -> Unit
) :
    ListAdapter<Ingredient, IngredientsAdapter.IngredientsViewHolder>(DiffCallback()) {

    companion object {
        private val DEFAULT_PATH_TO_IMAGE = "https://spoonacular.com/cdn/ingredients_100x100/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val itemBinding =
            IngridientsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(itemBinding, glide).apply {
            itemView.setOnClickListener {
                holderClicked(currentList[adapterPosition])
            }
        }
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
        fun bind(ingredient: Ingredient) {
            with(binding) {
                glide.load(DEFAULT_PATH_TO_IMAGE + ingredient.image).diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).placeholder(R.drawable.food_ic)
                    .circleCrop()
                    .into(ingredientImage)
                ingredientsTitle.text = ingredient.originalString
            }
        }

        fun unBind() {
            glide.clear(binding.ingredientImage)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient) =
            oldItem == newItem
    }
}