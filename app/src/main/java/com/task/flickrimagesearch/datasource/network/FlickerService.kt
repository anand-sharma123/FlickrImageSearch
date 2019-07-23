package com.task.flickrimagesearch.datasource.network

import com.task.flickrimagesearch.model.SearchResponseModel
import com.task.flickrimagesearch.utils.AppUtils
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerService {
    @GET("services/rest?method=" + AppUtils.FLICKR_METHOD + "&api_key="+AppUtils.API_KEY+"&format=json" +
            "&nojsoncallback=1&extras=url_n&safe_search=1")
    fun searchImage(@Query("text") query: String,
                    @Query("page") page: Int): Flowable<Response<SearchResponseModel>>
}