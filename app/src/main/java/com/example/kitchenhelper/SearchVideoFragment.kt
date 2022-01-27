package com.example.kitchenhelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kitchenhelper.databinding.FragmentSearchVideoBinding


class SearchVideoFragment : Fragment() {

    private lateinit var searchVideoViewBinding: FragmentSearchVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchVideoViewBinding = FragmentSearchVideoBinding.inflate(inflater, container, false)
        return searchVideoViewBinding.root
    }

}