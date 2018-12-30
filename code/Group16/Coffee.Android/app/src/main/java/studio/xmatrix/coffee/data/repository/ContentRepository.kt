package studio.xmatrix.coffee.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.network.*
import studio.xmatrix.coffee.data.service.ContentDatabase
import studio.xmatrix.coffee.data.service.ContentService
import studio.xmatrix.coffee.data.service.resource.ContentsResource
import studio.xmatrix.coffee.data.service.response.CommonResponse
import studio.xmatrix.coffee.data.service.response.PublishContentResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val app: App,
    private val executors: AppExecutors,
    private val service: ContentService,
    private val database: ContentDatabase
) {

    fun getPublic(): LiveData<Resource<ContentsResource>> {
        return object : NetworkBoundResource<ContentsResource, PublishContentResponse>(executors) {
            override fun saveCallResult(item: PublishContentResponse) {
                if (item.state == CommonResponse.StatusSuccess) {

                }
            }

            override fun shouldFetch(data: ContentsResource?) = true
            override fun loadFromDb() = MutableLiveData<ContentsResource>().also { it.value = null }
            override fun createCall() = service.getPublic(1,2)
        }.asLiveData()
    }
}
