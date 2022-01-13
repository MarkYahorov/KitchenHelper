package com.example.kitchenhelper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kitchenhelper.databinding.FragmentSearchRecipeBinding
import com.example.kitchenhelper.presentation.search.di.SearchComponent
import com.example.kitchenhelper.presentation.search.viewModel.SearchRecipeViewModel
import dagger.Lazy
import javax.inject.Inject

class SearchRecipeFragment : Fragment() {

    private lateinit var viewBinding: FragmentSearchRecipeBinding

    @Inject
    lateinit var factory: Lazy<SearchRecipeViewModel.Factory>
    private val viewModel: SearchRecipeViewModel by viewModels { factory.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SearchComponent.create(App.appComponent, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.caloriesSeekBar.setOnRangeSeekBarChangeListener { bar, minValue, maxValue ->
            Log.e("TAG", "MIN $minValue  MAX $maxValue")
        }
    }

}