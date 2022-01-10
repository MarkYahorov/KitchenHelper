package com.example.kitchenhelper.presentation.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenhelper.databinding.IngridientsListItemBinding
import com.example.kitchenhelper.presentation.random.model.Ingredients

class RandomRecipeAdapter :
    ListAdapter<Ingredients, RandomRecipeAdapter.RecipeViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding =
            IngridientsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class RecipeViewHolder(
        private val binding: IngridientsListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: Ingredients) {
            with(binding) {
                ingredientsTitle.text = ingredients.originalString
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ingredients>() {
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients) =
            oldItem == newItem
    }
}