package com.example.kitchenhelper.core

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun IntArray.toFloatArray(): FloatArray {
    val floatList = mutableListOf<Float>()
    this.forEach {
        floatList.add(it.toFloat())
    }
    return floatList.toFloatArray()
}

inline fun <reified myViewModel : ViewModel> createViewModel(
    fragment: Fragment,
    viewModel: myViewModel
): myViewModel {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel as T
        }
    }
    return ViewModelProvider(fragment, factory)[viewModel::class.java]
}