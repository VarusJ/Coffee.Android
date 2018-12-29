package studio.xmatrix.coffee.data.service

import android.arch.lifecycle.LiveData
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import studio.xmatrix.coffee.data.common.network.ApiResponse
import studio.xmatrix.coffee.data.service.response.CommonResponse
import studio.xmatrix.coffee.data.service.response.ContentResponse
import studio.xmatrix.coffee.data.service.response.PublishContentResponse
import javax.inject.Singleton

@Singleton
interface ContentService {

    companion object {
        const val BASE_URL = "$SERVICE_BASE_URL/content/"
    }

    @DELETE("{id}")
    fun deleteById(@Path("id") contentId: String): LiveData<ApiResponse<CommonResponse>>

    @POST("album")

    @GET("detail/{id}")
    fun getDetailById(@Path("id") contentId: String): LiveData<ApiResponse<ContentResponse>>

    @GET("public")
    fun getPublic(): LiveData<ApiResponse<PublishContentResponse>>

}
