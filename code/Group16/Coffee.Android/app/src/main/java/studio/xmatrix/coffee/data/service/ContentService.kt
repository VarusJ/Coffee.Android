package studio.xmatrix.coffee.data.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.network.ApiResponse
import studio.xmatrix.coffee.data.common.network.AppExecutors
import studio.xmatrix.coffee.data.model.Content
import studio.xmatrix.coffee.data.model.Content_
import studio.xmatrix.coffee.data.service.resource.ContentsResource
import studio.xmatrix.coffee.data.service.response.CommonResponse
import studio.xmatrix.coffee.data.service.response.ContentResponse
import studio.xmatrix.coffee.data.service.response.PublishContentResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ContentService {

    companion object {
        const val BASE_URL = "$SERVICE_BASE_URL/content/"
    }

    @DELETE("{id}")
    fun deleteById(@Path("id") contentId: String): LiveData<ApiResponse<CommonResponse>>

    // @POST("album")

    @GET("detail/{id}")
    fun getDetailById(@Path("id") contentId: String): LiveData<ApiResponse<ContentResponse>>

    @GET("public?page={page}&eachPage={eachPage}")
    fun getPublic(@Path("page") page: Int, @Path("eachPage") eachPage: Int): LiveData<ApiResponse<PublishContentResponse>>
}

@Singleton
class ContentDatabase @Inject constructor(app: App, private val executors: AppExecutors) {

    private val box: Box<Content> = app.boxStore.boxFor()

    fun loadContentsByNum(num: Int): LiveData<ContentsResource> {
        val data = MutableLiveData<ContentsResource>()
        executors.diskIO().execute {
            val res = ContentsResource(CommonResponse.StatusSuccess, box.query {
                equal(Content_.publicContent, true)
                orderDesc(Content_.publishDate)
            }.find(0, num.toLong()))
            data.postValue(res)
        }
        return data
    }

    fun saveContents(items: List<Content>) {
        box.query {
            equal(Content_.publicContent, true)
            or()
        }
    }
}
