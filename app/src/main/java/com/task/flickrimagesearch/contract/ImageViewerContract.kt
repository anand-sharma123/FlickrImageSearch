package com.task.flickrimagesearch.contract

import android.widget.ImageView

interface ImageViewerContract {
    interface View {
        fun getImageView(): ImageView
    }

    interface Presenter {
        fun loadImage(url: String = "")
    }
}