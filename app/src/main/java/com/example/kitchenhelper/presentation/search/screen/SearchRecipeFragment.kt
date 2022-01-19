package com.example.kitchenhelper.presentation.search.screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchRecipeFragment : Fragment() {

    companion object {
        private const val FIRST_POSITION = 1
    }

    private val args by navArgs<SearchRecipeFragmentArgs>()
    private lateinit var viewBinding: FragmentSearchRecipeBinding
    private lateinit var searchViewModel: SearchRecipeViewModel
    private val component by lazy {
        SearchComponent.create(App.appComponent, this)
    }
    private val searchAdapter by lazy {
        SearchListAdapter(Glide.with(this))
    }
    private val methodManager by lazy {
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }
    private val queryTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { searchViewModel.setQuery(s.toString()) }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val equipmentTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null && s.toString() != "") {
                searchViewModel.setEquipment(s.toString())
            } else {
                searchViewModel.setEquipment(null)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val timeTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null && s.toString() != "") {
                searchViewModel.setMaxReadyTime(s.toString().toInt())
            } else {
                searchViewModel.setMaxReadyTime(null)
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
            searchViewModel.setQuery(args.recipeQuery)
            getRecipes()
        }
        setTextToEditText()
        initRecycler()

        lifecycleScope.launch {
            searchViewModel.recipesFlow.collectLatest { data ->
                data?.let {
                    searchAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            searchViewModel.error.collect { showErrorDialog(it) }
        }
        changeEditTextKeyboardAction()
    }

    private fun changeEditTextKeyboardAction() {
        with(viewBinding) {
            searchFragmentEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getRecipes()
                    setNewLoadingState(searchFragmentEditText)
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun setNewLoadingState(view: View) {
        methodManager?.hideSoftInputFromWindow(view.windowToken, 0)
        viewBinding.searchRecycler.scrollToPosition(FIRST_POSITION)
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
                    viewBinding.searchRecycler.scrollToPosition(FIRST_POSITION)
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
        viewBinding.searchFragmentEditText.setText(searchViewModel.getQuery())
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
        if (requireActivity().isFinishing) {
            SearchComponent.clear()
        }
        super.onStop()
    }

}