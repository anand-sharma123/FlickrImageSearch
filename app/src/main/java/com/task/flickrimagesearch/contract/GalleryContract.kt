package com.task.flickrimagesearch.contract

import com.task.flickrimagesearch.model.PhotoItems

interface GalleryContract {

    interface View {

        fun displayPhotos(photoItemList: List<PhotoItems>)

        fun showError(message: String)

        fun showNoResult()

        fun showLoading()

        fun hideLoading()

        fun launchImageViewerActivity(photoId: String)

        fun refreshList()

        fun showPageLoading()

        fun hidePageLoading()

        fun launchImageViewActivity(url: String)
    }

    interface Presenter {

        fun searchPhotos(query: String = "")
        fun loadNextPage(query: String)
        fun onImageClicked(position: Int)

    }
}