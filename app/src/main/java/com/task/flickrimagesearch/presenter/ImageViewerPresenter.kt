package com.task.flickrimagesearch.presenter

import com.task.flickrimagesearch.contract.ImageViewerContract
import com.task.flickrimagesearch.utils.loadImageFromLinkWithLoadingProgress

class ImageViewerPresenter(private val view: ImageViewerContract.View) : ImageViewerContract.Presenter {
    override fun loadImage(url: String) {
        view.getImageView().loadImageFromLinkWithLoadingProgress(url)
    }
}