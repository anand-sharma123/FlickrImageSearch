package com.task.flickrimagesearch.view.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.task.flickrimagesearch.R
import com.task.flickrimagesearch.contract.GalleryContract
import com.task.flickrimagesearch.model.PhotoItems
import com.task.flickrimagesearch.presenter.GalleryPresenter
import com.task.flickrimagesearch.utils.AppUtils
import com.task.flickrimagesearch.view.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.activity_main.*

class GalleryActivity : AppCompatActivity(), GalleryContract.View {

    private lateinit var searchView: SearchView
    private lateinit var itemAdapter: GalleryAdapter
    private lateinit var galleryPresenter: GalleryContract.Presenter
    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getText(R.string.app_name)
        toolbar.setTitleTextColor(Color.WHITE)
        initRecyclerView()
        galleryPresenter = GalleryPresenter(this)
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        photo_recycler_view.layoutManager = gridLayoutManager

        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
                if (!(galleryPresenter as GalleryPresenter).isLoading && !(galleryPresenter as GalleryPresenter).isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                        galleryPresenter.loadNextPage(searchView.query.toString())
                    }
                }
            }
        }
        photo_recycler_view.addOnScrollListener(recyclerViewOnScrollListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gallery_menu, menu)
        val searchMenu = menu?.findItem(R.id.action_search)
        searchView = searchMenu?.actionView as SearchView
        searchView.queryHint = getString(R.string.abc_search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                galleryPresenter.searchPhotos(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun displayPhotos(photoItemList: List<PhotoItems>) {
        photo_recycler_view.visibility = View.VISIBLE
        no_result_text.visibility = View.GONE
        itemAdapter = GalleryAdapter(this, photoItemList, object : GalleryAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                if (view != null)
                    galleryPresenter.onImageClicked(position)
            }
        })
        photo_recycler_view.adapter = itemAdapter
    }

    override fun launchImageViewActivity(url: String) {
        val intent = Intent(this, ImageViewerActivity::class.java)
        intent.putExtra(AppUtils.INTENT_ARG_PHOTO_URL, url)
        startActivity(intent)
    }

    override fun refreshList() {
        photo_recycler_view.adapter.notifyDataSetChanged()
    }

    override fun showNoResult() {
        photo_recycler_view.visibility = View.GONE
        no_result_text.visibility = View.VISIBLE
    }

    override fun showLoading() {
        if(progressDialog == null)
            progressDialog = initDialog()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun launchImageViewerActivity(photoId: String) {

    }

    override fun showError(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message as CharSequence, Snackbar.LENGTH_SHORT)
        val sbView = snackBar.view
        val textView = sbView
            .findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    override fun showPageLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hidePageLoading() {
        progress_bar.visibility = View.GONE
    }

    fun Activity.initDialog():Dialog {
        val progressDialog = Dialog(this)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }


}
