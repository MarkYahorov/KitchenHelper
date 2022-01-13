package com.example.kitchenhelper.core

fun IntArray.toFloatArray(): FloatArray {
    val floatList = mutableListOf<Float>()
    this.forEach {
        floatList.add(it.toFloat())
    }
    return floatList.toFloatArray()
}