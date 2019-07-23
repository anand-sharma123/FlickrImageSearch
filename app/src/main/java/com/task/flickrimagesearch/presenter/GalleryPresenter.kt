package com.task.flickrimagesearch.presenter

import com.task.flickrimagesearch.contract.GalleryContract
import com.task.flickrimagesearch.datasource.network.AppRepository
import com.task.flickrimagesearch.model.PhotoItems
import com.task.flickrimagesearch.model.Photos
import com.task.flickrimagesearch.utils.AppUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GalleryPresenter(private val view: GalleryContract.View?) : GalleryContract.Presenter {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null
    private var photoList: MutableList<PhotoItems> = mutableListOf()
    internal var isLastPage = false
    internal var isLoading = false
    internal var currentPage: Int = 1

    override fun searchPhotos(query: String) {
        view?.showLoading()
        if (disposable != null)
            compositeDisposable.delete(disposable!!)

        disposable = AppRepository.createService().searchImage(query, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                view?.hideLoading()
                val photos: Photos = t.body()!!.photos
                if (photos.page == photos.pages)
                    isLastPage = true
                val listPhotos =  photos.photo
                if (!listPhotos.isNullOrEmpty()) {
                    photoList.clear()
                    photoList.addAll(listPhotos)
                    view?.displayPhotos(photoList)
                } else {
                    view?.showNoResult()
                }
            }, { throwable ->
                view?.hideLoading()
                view?.showError(throwable.localizedMessage)
            })

        compositeDisposable.add(disposable!!)
    }

    override fun loadNextPage(query: String) {
        if(isLastPage)
            return

        currentPage++

        isLoading = true

        if (disposable != null)
            compositeDisposable.delete(disposable!!)

        view?.showPageLoading()

        disposable = AppRepository.createService().searchImage(query, currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ t ->
                view?.hidePageLoading()
                isLoading = false
                val photos: Photos = t.body()!!.photos
                if (photos.page == photos.pages)
                    isLastPage = true
                val listPhotos =  photos.photo
                if (!listPhotos.isNullOrEmpty()) {
                    photoList.addAll(listPhotos)
                    view?.refreshList()
                }
            }, { throwable ->
                view?.hidePageLoading()
                isLoading = false
            })

        compositeDisposable.add(disposable!!)

    }

    override fun onImageClicked(position: Int) {
        view?.launchImageViewActivity(AppUtils.getFlickrImageLink(photoList[position].id,
            photoList[position].secret, photoList[position].server, photoList[position].farm))
    }
}