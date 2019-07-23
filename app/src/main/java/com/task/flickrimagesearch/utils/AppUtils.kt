package com.task.flickrimagesearch.utils


object AppUtils {
    const val FLICKR_METHOD = "flickr.photos.search"
    const val API_KEY = "aa26679bb06bff46497df7f1e970b178"
    const val INTENT_ARG_PHOTO_URL = "PHOTO_URL"

    fun getFlickrImageLink(id: String, secret: String, serverId: String, farmId: Int): String {
        return "https://farm$farmId.staticflickr.com/$serverId/${id}_${secret}.jpg"
    }

}