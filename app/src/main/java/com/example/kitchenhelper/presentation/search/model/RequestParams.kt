package com.example.kitchenhelper.presentation.search.model

data class RequestParams(
    var query: String,
    var equipment:String? = null,
    var maxReadyTime: Int? = null,
    var calories: Pair<Int, Int>? = null,
)