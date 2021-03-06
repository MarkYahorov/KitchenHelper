package com.example.kitchenhelper.presentation.random.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.App
import com.example.kitchenhelper.R
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.core.getQuantityString
import com.example.kitchenhelper.databinding.FragmentRandomBinding
import com.example.kitchenhelper.presentation.random.adapter.IngredientsAdapter
import com.example.kitchenhelper.presentation.random.di.RandomComponent
import com.example.kitchenhelper.presentation.random.viewModel.RandomViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RandomFragment : Fragment() {

    private lateinit var randomViewModel: RandomViewModel
    private lateinit var randomViewBinding: FragmentRandomBinding
    private val glide by lazy {
        Glide.with(this)
    }
    private val component by lazy {
        RandomComponent.createComponent(App.appComponent, this)
    }
    private val randomAdapter by lazy {
        IngredientsAdapter(glide) {
            Toast.makeText(requireContext(), "sdfsafdasdf", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomViewModel = createViewModel(this, component.randomViewModel)
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

            restoreAnimationState(savedInstanceState)

        initRecycler()
        getRecipe()
        setNotLoadingState()
        lifecycleScope.launchWhenStarted {
            randomViewModel.errorFlow.collect {
                showErrorDialog(it)
                configureEmptyStateVisibility(isVisible = true)
            }
        }
    }

    private fun restoreAnimationState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            randomViewModel.transitionId?.let {
                randomViewBinding.randomMotion.transitionToState(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setSearchBtnClickListener()
        setRefreshListener()
             saveAnimationState()
    }

    private fun setSearchBtnClickListener() {
        with(randomViewBinding) {
            searchBtn.setOnClickListener {
                if (searchEditText.text.isNotBlank()) {
                    val searchAction =
                        RandomFragmentDirections.actionRandomFragmentToSearchRecipeFragment(
                            searchEditText.text.toString()
                        )
                    findNavController().navigate(searchAction)
                } else {
                    Toast.makeText(requireContext(), R.string.empty_string, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setRefreshListener() {
        with(randomViewBinding) {
            randomRefreshLayout.setOnRefreshListener {
                randomViewModel.getRecipes()
            }
        }
    }

    private fun saveAnimationState() {
        randomViewBinding.randomMotion.addTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                randomViewModel.transitionCompleted(currentId)
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })
    }

    private fun getRecipe() {
        lifecycleScope.launchWhenStarted {
            randomViewModel.randomRecipesFlow.collect {
                it?.let {
                    with(randomViewBinding) {
                        glide.load(it.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.food_ic)
                            .into(recipeImage)
                        randomViewBinding.randomRefreshLayout.minimumHeight = 1440
                        recipeTitle.text = it.title
                        recipeReadyTime.text = getQuantityString(
                            R.plurals.minutes,
                            it.readyTime,
                            getString(R.string.ready_time),
                            activity?.resources
                        )
                        recipeServings.text = getQuantityString(
                            R.plurals.servings,
                            it.servings,
                            getString(R.string.servings),
                            activity?.resources
                        )
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
                randomViewBinding.randomRefreshLayout.isRefreshing = it
            }
        }
    }

    private fun configureEmptyStateVisibility(isVisible: Boolean) {
        randomViewBinding.emptyStateScreen.root.isVisible = isVisible
    }

    private fun showErrorDialog(throwable: Throwable) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ERROR")
            .setMessage(throwable.message)
            .setPositiveButton("Ok") { dialog, _ ->
                randomViewModel.getRecipes()
                configureEmptyStateVisibility(isVisible = false)
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

    override fun onStop() {
        if (requireActivity().isFinishing) {
            RandomComponent.clearComponent()
        }
        with(randomViewBinding) {
            randomRefreshLayout.setOnRefreshListener(null)
            searchBtn.setOnClickListener(null)
            randomMotion.setTransitionListener(null)
        }

        super.onStop()
    }
}