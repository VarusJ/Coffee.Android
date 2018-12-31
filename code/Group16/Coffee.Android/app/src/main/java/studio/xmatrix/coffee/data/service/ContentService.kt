package studio.xmatrix.coffee.data.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import studio.xmatrix.coffee.data.common.network.ApiResponse
import studio.xmatrix.coffee.data.common.network.AppExecutors
import studio.xmatrix.coffee.data.model.*
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

    @GET("public")
    fun getPublic(@Query("page") page: Int, @Query("eachPage") eachPage: Int): LiveData<ApiResponse<PublishContentResponse>>
}

@Singleton
class ContentDatabase @Inject constructor(app: studio.xmatrix.coffee.App, private val executors: AppExecutors) {

    private val box: Box<Content> = app.boxStore.boxFor()

    private val albumBox: Box<Album> = app.boxStore.boxFor()
    private val appBox: Box<App> = app.boxStore.boxFor()
    private val fileBox: Box<File> = app.boxStore.boxFor()
    private val imageBox: Box<Image> = app.boxStore.boxFor()
    private val movieBox: Box<Movie> = app.boxStore.boxFor()

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

    fun saveContents(items: List<Content>?) {
        box.query {
            equal(Content_.publicContent, true)
        }.find().forEach {
            if (!it.album.isNull) albumBox.remove(it.album.targetId)
            if (!it.app.isNull) appBox.remove(it.app.targetId)
            if (!it.movie.isNull) movieBox.remove(it.movie.targetId)
            it.files.forEach { file -> fileBox.remove(file._id) }
            it.images.forEach { image -> imageBox.remove(image._id) }
            box.remove(it._id)
        }
        items?.forEach { item ->
            box.query {
                equal(Content_.id, item.id)
            }.find().forEach {
                if (!it.album.isNull) albumBox.remove(it.album.targetId)
                if (!it.app.isNull) appBox.remove(it.app.targetId)
                if (!it.movie.isNull) movieBox.remove(it.movie.targetId)
                it.files.forEach { file -> fileBox.remove(file._id) }
                it.images.forEach { image -> imageBox.remove(image._id) }
                box.remove(it._id)
            }
        }
        box.put(items)
    }
}
