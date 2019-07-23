package com.task.flickrimagesearch.utils


object AppUtils {
    const val FLICKR_METHOD = "flickr.photos.search"
    const val API_KEY = "dc2242530334eff5c97106c9110de945"
    const val INTENT_ARG_PHOTO_URL = "PHOTO_URL"

    fun getFlickrImageLink(id: String, secret: String, serverId: String, farmId: Int): String {
        return "https://farm$farmId.staticflickr.com/$serverId/${id}_${secret}.jpg"
    }

}