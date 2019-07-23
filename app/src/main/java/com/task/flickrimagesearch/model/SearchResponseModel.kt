package com.task.flickrimagesearch.model

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: List<PhotoItems>
)

data class SearchResponseModel(
    val stat: String,
    val photos: Photos)