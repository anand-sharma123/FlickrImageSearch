package com.task.flickrimagesearch.view.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import com.task.flickrimagesearch.R
import com.task.flickrimagesearch.contract.ImageViewerContract
import com.task.flickrimagesearch.presenter.ImageViewerPresenter
import com.task.flickrimagesearch.utils.AppUtils
import kotlinx.android.synthetic.main.activity_image_viewer.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class ImageViewerActivity : AppCompatActivity(), ImageViewerContract.View  {

    private lateinit var imageViewPresenter: ImageViewerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getText(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)
        imageViewPresenter = ImageViewerPresenter(this)
        imageViewPresenter.loadImage(intent.getStringExtra(AppUtils.INTENT_ARG_PHOTO_URL))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getImageView(): ImageView {
        return image_viewer
    }

}