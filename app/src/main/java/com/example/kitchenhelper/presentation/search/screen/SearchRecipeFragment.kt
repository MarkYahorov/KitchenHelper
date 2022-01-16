package com.example.kitchenhelper.presentation.search.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kitchenhelper.App
import com.example.kitchenhelper.R
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.databinding.FragmentSearchRecipeBinding
import com.example.kitchenhelper.presentation.search.adapter.SearchListAdapter
import com.example.kitchenhelper.presentation.search.di.SearchComponent
import com.example.kitchenhelper.presentation.search.viewModel.SearchRecipeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SearchRecipeFragment : Fragment() {

    private val args by navArgs<SearchRecipeFragmentArgs>()
    private lateinit var viewBinding: FragmentSearchRecipeBinding
    private lateinit var searchViewModel: SearchRecipeViewModel
    private val component by lazy {
        SearchComponent.create(App.appComponent, this)
    }
    private val searchAdapter by lazy {
        SearchListAdapter(Glide.with(this))
    }
    private val queryTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { searchViewModel.requestParams.query = s.toString() }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val equipmentTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null && s.toString() != "") {
                searchViewModel.requestParams.equipment = s.toString()
            } else {
                searchViewModel.requestParams.equipment = null
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val timeTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null && s.toString() != "") {
                searchViewModel.requestParams.maxReadyTime = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = createViewModel(this, component.searchViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSearchRecipeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            searchViewModel.requestParams.query = args.recipeQuery
            getRecipes()
        }
        setTextToEditText()
        initRecycler()
        lifecycleScope.launchWhenStarted {
            searchViewModel.recipes.collect { searchAdapter.submitList(it) }
        }
        lifecycleScope.launchWhenStarted {
            searchViewModel.error.collect { showErrorDialog(it) }
        }
        changeEditTextKeyboardAction()
    }

    private fun changeEditTextKeyboardAction() {
        with(viewBinding) {
            searchFragmentEditText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getRecipes()
                }
                false
            }
        }
    }


    private fun showErrorDialog(throwable: Throwable) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("ERROR")
            .setMessage(throwable.message)
            .setPositiveButton("Ok") { dialog, _ ->
                searchViewModel.searchRecipes()
//                configureEmptyStateVisibility(isVisible = false)
                dialog.dismiss()
            }
            .show()
    }

    private fun initRecycler() {
        with(viewBinding.searchRecycler) {
            adapter = searchAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()

        setChangeTextListener()
        setSearchBtnClickListener()
        setRangeCaloriesListener()
    }


    private fun setRangeCaloriesListener() {
        viewBinding.searchCaloriesSeekBar.setOnRangeSeekBarChangeListener { _, minValue, maxValue ->
            if (minValue is Int && maxValue is Int) {
                searchViewModel.setCalories(Pair(minValue.toInt(), maxValue.toInt()))
            }
        }
    }

    private fun setSearchBtnClickListener() {
        with(viewBinding) {
            searchBtn.setOnClickListener {
                if (searchFragmentEditText.text.isBlank()) {
                    getRecipes()
                } else {
                    Toast.makeText(requireContext(), R.string.empty_string, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setChangeTextListener() {
        with(viewBinding) {
            searchFragmentEditText.addTextChangedListener(queryTextWatcher)
            searchReadyTimeEditText.addTextChangedListener(timeTextWatcher)
            equipmentEditText.addTextChangedListener(equipmentTextWatcher)
        }
    }

    private fun setTextToEditText() {
        viewBinding.searchFragmentEditText.setText(searchViewModel.requestParams.query)
    }

    private fun getRecipes() {
        searchViewModel.searchRecipes()
    }

    override fun onStop() {
        with(viewBinding) {
            searchFragmentEditText.removeTextChangedListener(queryTextWatcher)
            searchReadyTimeEditText.removeTextChangedListener(timeTextWatcher)
            equipmentEditText.removeTextChangedListener(equipmentTextWatcher)
            searchBtn.setOnClickListener(null)
            searchCaloriesSeekBar.setOnRangeSeekBarChangeListener(null)
        }
        super.onStop()
    }

}