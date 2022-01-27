package com.example.kitchenhelper.presentation.searchVideo.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kitchenhelper.App
import com.example.kitchenhelper.R
import com.example.kitchenhelper.core.createViewModel
import com.example.kitchenhelper.databinding.FragmentSearchVideoBinding
import com.example.kitchenhelper.presentation.searchVideo.adapter.SearchVideoAdapter
import com.example.kitchenhelper.presentation.searchVideo.di.SearchVideoComponent
import com.example.kitchenhelper.presentation.searchVideo.viewModel.SearchVideoViewModel
import kotlinx.coroutines.launch


class SearchVideoFragment : Fragment() {

    private lateinit var searchVideoViewBinding: FragmentSearchVideoBinding
    private lateinit var searchVideoViewModel: SearchVideoViewModel
    private val component by lazy {
        SearchVideoComponent.create(App.appComponent, this)
    }
    private val searchVideoAdapter by lazy {
        SearchVideoAdapter(Glide.with(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchVideoViewModel = createViewModel(this, component.searchVideoViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchVideoViewBinding = FragmentSearchVideoBinding.inflate(inflater, container, false)
        return searchVideoViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        setSearchBtnListener()

        lifecycleScope.launch {
            searchVideoViewModel.videoFlow.collect { pagingVideo ->
                pagingVideo?.let {
                    searchVideoAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            searchVideoViewModel.errorFlow.collect {
                showErrorDialog(it.message)
            }
        }
    }

    private fun showErrorDialog(message: String?) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setSearchBtnListener() {
        with(searchVideoViewBinding) {
            searchVideoBtn.setOnClickListener {
                searchVideoViewModel.searchVideos(searchVideoEditText.text.toString())
            }
        }
    }

    private fun initRecycler() {
        with(searchVideoViewBinding.searchVideoRecycler) {
            adapter = searchVideoAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}