package com.example.kitchenhelper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kitchenhelper.databinding.FragmentRandomBinding
import com.example.kitchenhelper.presentation.random.adapter.RandomRecipeAdapter
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
        RandomRecipeAdapter()
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

        initRecycler()
        lifecycleScope.launchWhenStarted {
            randomViewModel.randomRecipesFlow.collect {
                if(it?.id != null) {
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
                } else {
                   Log.e("TAG", "fragment $it")
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            randomViewModel.errorFlow.collect { showErrorDialog(it) }
        }
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