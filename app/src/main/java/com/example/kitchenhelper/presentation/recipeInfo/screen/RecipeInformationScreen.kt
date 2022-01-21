package com.example.kitchenhelper.presentation.recipeInfo.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kitchenhelper.App
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.core.getQuantityString
import com.example.kitchenhelper.databinding.FragmentRecipeInformationBinding
import com.example.kitchenhelper.presentation.recipeInfo.adapter.RecipeIngredientsAdapter
import com.example.kitchenhelper.presentation.recipeInfo.di.RecipeInfoComponent
import com.example.kitchenhelper.presentation.recipeInfo.model.RecipeInfo
import com.example.kitchenhelper.presentation.recipeInfo.viewModel.RecipeInfoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class RecipeInformationScreen : Fragment() {

    private val args by navArgs<RecipeInformationScreenArgs>()
    private lateinit var recipeInfoViewBinding: FragmentRecipeInformationBinding
    private val component by lazy {
        RecipeInfoComponent.create(App.appComponent, this)
    }
    private val glide by lazy {
        Glide.with(this)
    }
    private val recipeAdapter by lazy {
        RecipeIngredientsAdapter(glide)
    }
    private lateinit var recipeInfoViewModel: RecipeInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeInfoViewModel = createViewModel(this, component.recipeInfoViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        recipeInfoViewBinding = FragmentRecipeInformationBinding.inflate(inflater, container, false)
        return recipeInfoViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeInfoViewModel.getRecipe(args.recipeId)

        initRecycler()
        handleError()
        lifecycleScope.launch {
            recipeInfoViewModel.recipeFlow.collect { recipe ->
                recipe?.let { handleRecipe(it) }
            }
        }
    }

    private fun initRecycler() {
        with(recipeInfoViewBinding.ingredientsInfoRecycler) {
            adapter = recipeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun handleRecipe(recipe: RecipeInfo) {
        with(recipeInfoViewBinding) {
            glide.load(recipe.image)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC)
                .placeholder(com.example.kitchenhelper.R.drawable.food_ic)
                .into(recipeInfoImage)
            recipeInfoTitle.text = recipe.title
            recipeInfoReadyTime.text = getQuantityString(
                com.example.kitchenhelper.R.plurals.minutes,
                recipe.readyTime,
                getString(com.example.kitchenhelper.R.string.ready_time),
                activity?.resources
            )
            recipeInfoServings.text = getQuantityString(
                com.example.kitchenhelper.R.plurals.servings,
                recipe.servings,
                getString(com.example.kitchenhelper.R.string.servings),
                activity?.resources
            )
            recipeInfoInstructions.text = recipe.instructions
        }
        recipeAdapter.submitList(recipe.ingredients)
    }

    private fun handleError() {
        lifecycleScope.launch {
            recipeInfoViewModel.errorFlow.collect { showErrorDialog(it) }
        }
    }

    private fun showErrorDialog(throwable: Throwable) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ERROR")
            .setMessage(throwable.message)
            .setPositiveButton("Ok") { dialog, _ ->
                recipeInfoViewModel.getRecipe(args.recipeId)
                dialog.dismiss()
            }
            .show()
    }
}