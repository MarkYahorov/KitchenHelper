package com.example.kitchenhelper.presentation.recipeInfo.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.kitchenhelper.App
import com.example.kitchenhelper.R
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.databinding.FragmentRecipeInformationBinding
import com.example.kitchenhelper.presentation.recipeInfo.di.RecipeInfoComponent
import com.example.kitchenhelper.presentation.recipeInfo.viewModel.RecipeInfoViewModel

class RecipeInformationScreen : Fragment() {

    private val args by navArgs<RecipeInformationScreenArgs>()
    private lateinit var recipeInfoViewBinding: FragmentRecipeInformationBinding
    private val component by lazy {
        RecipeInfoComponent.create(App.appComponent, this)
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

    }

    private fun initRecycler() {
        TODO("Not yet implemented")
    }
}