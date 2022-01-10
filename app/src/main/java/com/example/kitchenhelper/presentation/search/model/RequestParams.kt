package com.example.kitchenhelper.presentation.search.model

data class RequestParams(
    var query: String?,
    var equipment:String?,
    var maxReadyTime: Int?,
    var calories: Pair<Int, Int> = Pair(0,100),
)