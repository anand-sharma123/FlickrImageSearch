package com.task.flickrimagesearch.ui

import com.task.flickrimagesearch.contract.GalleryContract
import com.task.flickrimagesearch.model.PhotoItems
import com.task.flickrimagesearch.presenter.GalleryPresenter
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)
class GalleryPresenterTest {
    @Mock
    internal var mMockGalleryView: GalleryContract.View? = null

    private lateinit var mGalleryPresenter: GalleryPresenter

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    // this prevents StackOverflowErrors when scheduling with a delay
                    return super.scheduleDirect(run, 0, unit)
                }

                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }
    }


    @Before
    fun setUp() {
        mGalleryPresenter = GalleryPresenter(mMockGalleryView)
    }

    @Test
    fun search_ItemsAvailable_showItems() {

        mGalleryPresenter.searchPhotos("human")

        Mockito.verify(mMockGalleryView)?.showLoading()
        Mockito.verify(mMockGalleryView)?.hideLoading()
        Mockito.verify(mMockGalleryView)?.displayPhotos(mGalleryPresenter.getReponseList())
    }

    @Test
    fun search_ItemsUnavailable_showEmpty() {
        mGalleryPresenter.searchPhotos("asdgkjgsdg")//Error String for failed test

        Mockito.verify(mMockGalleryView)?.showLoading()
        Mockito.verify(mMockGalleryView)?.hideLoading()
        Mockito.verify(mMockGalleryView)?.showNoResult()
    }

    @Test
    fun loadNextPage_ItemsAvailable_refreshItems() {

        //make current page as 1
        mGalleryPresenter.currentPage = 1

        //test load page one
        mGalleryPresenter.loadNextPage("human")
        assertEquals(mGalleryPresenter.isLastPage, false)
        assertEquals(mGalleryPresenter.currentPage, 2)

        //test load page two
        mGalleryPresenter.loadNextPage("human")
        assertEquals(mGalleryPresenter.isLastPage, false)
        assertEquals(mGalleryPresenter.currentPage, 3)

        Mockito.verify(mMockGalleryView, Mockito.times(2))?.showPageLoading()
        Mockito.verify(mMockGalleryView, Mockito.times(2))?.hidePageLoading()
    }


    @Test
    fun loadNextPage_ItemsUnavailable_notRefreshItems() {

        //set current page to 14
        mGalleryPresenter.currentPage = 14

        //test load page three
        mGalleryPresenter.loadNextPage("KOTLIN")

        Mockito.verify(mMockGalleryView)?.showPageLoading()
        Mockito.verify(mMockGalleryView)?.hidePageLoading()
        Mockito.verify(mMockGalleryView, Mockito.never())?.refreshList()
    }

    @Test
    fun onImageClick_launchPhotoViewActivity() {

        //test with no elements in photo list
        mGalleryPresenter.photoList = mutableListOf()
        mGalleryPresenter.onImageClicked(0)

        //test with elements in photo list
        mGalleryPresenter.photoList =  mutableListOf(PhotoItems("1", "TEST", "", "",
            0, "", 0, "", "100", "200"))

        mGalleryPresenter.onImageClicked(0)

        mGalleryPresenter.onImageClicked(mGalleryPresenter.photoList.size)

        //should one be invoked once
        Mockito.verify(mMockGalleryView)?.launchImageViewActivity("https://farm0.staticflickr.com//1_.jpg")
    }
}