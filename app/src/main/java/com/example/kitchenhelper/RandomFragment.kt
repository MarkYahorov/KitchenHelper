package com.example.kitchenhelper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.databinding.FragmentRandomBinding
import com.example.kitchenhelper.presentation.random.adapter.IngredientsAdapter
import com.example.kitchenhelper.presentation.random.di.RandomComponent
import com.example.kitchenhelper.presentation.random.viewModel.RandomViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.Lazy
import javax.inject.Inject

class RandomFragment : Fragment() {

    @Inject
    lateinit var factory: Lazy<RandomViewModel.Factory>
    private val randomViewModel: RandomViewModel by viewModels { factory.get() }
    private lateinit var randomViewBinding: FragmentRandomBinding
    private val glide by lazy {
        Glide.with(this)
    }
    private val randomAdapter by lazy {
        IngredientsAdapter(glide)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RandomComponent.create(App.appComponent, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        randomViewBinding = FragmentRandomBinding.inflate(inflater, container, false)
        return randomViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showIngredients(randomViewModel.isIngredientsVisible)
        initRecycler()
        getRecipe()
        setSeeAllBtnClickListener()
        setNotLoadingState()
        setSwipeRefreshListener()
        lifecycleScope.launchWhenStarted {
            randomViewModel.errorFlow.collect { showErrorDialog(it) }
        }
    }

    private fun setSwipeRefreshListener() {
        randomViewBinding.randomSwipeRefresh.setOnRefreshListener {
            randomViewModel.getRecipes()
        }
    }

    private fun getRecipe() {
        lifecycleScope.launchWhenStarted {
            randomViewModel.randomRecipesFlow.collect {
                it?.let {
                    with(randomViewBinding) {
                        Log.e("TAG", "not null $it")
                        glide.load(it.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(recipeImage)
                        recipeTitle.text = it.title
                        recipeReadyTime.text = it.readyTime.toString()
                        recipeServings.text = it.servings.toString()
                        recipeInstructions.text = it.instructions
                    }
                    randomAdapter.submitList(it.ingredients)
                }
            }
        }
    }

    private fun setNotLoadingState() {
        lifecycleScope.launchWhenStarted {
            randomViewModel.notLoadingFlow.collect {
                randomViewBinding.randomSwipeRefresh.isRefreshing = it
            }
        }
    }

    private fun setSeeAllBtnClickListener() {
        with(randomViewBinding) {
            recipeSeeAllBtn.setOnClickListener {
                val isVisible = !randomViewModel.isIngredientsVisible
                showIngredients(isVisible)
                randomViewModel.isIngredientsVisible = isVisible
            }
        }
    }

    private fun showIngredients(isVisible: Boolean) {
        randomViewBinding.ingredientsRecycler.isVisible = isVisible
    }

    private fun showEmptyState() {
        TODO("Not yet implemented")
    }

    private fun showErrorDialog(throwable: Throwable) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ERROR")
            .setMessage(throwable.message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun initRecycler() {
        with(randomViewBinding.ingredientsRecycler) {
            adapter = randomAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}