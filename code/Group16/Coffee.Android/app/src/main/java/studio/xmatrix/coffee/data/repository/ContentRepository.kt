package studio.xmatrix.coffee.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.network.AppExecutors
import studio.xmatrix.coffee.data.common.network.NetworkBoundResource
import studio.xmatrix.coffee.data.common.network.NetworkDirectiveResource
import studio.xmatrix.coffee.data.common.network.Resource
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

    fun getPublicByPage(page: Int, eachPage: Int): LiveData<Resource<ContentsResource>> {
        if (page == 1) {
            var state = String()
            return object : NetworkBoundResource<ContentsResource, PublishContentResponse>(executors) {
                override fun saveCallResult(item: PublishContentResponse) {
                    state = item.state
                    if (item.state == CommonResponse.StatusSuccess) {
                        database.saveContents(item.toContentsResource().resource)
                    }
                }

                override fun shouldFetch(data: ContentsResource?) = true

                override fun loadFromDb(): LiveData<ContentsResource> {
                    if (state == CommonResponse.StatusSuccess) {
                        return database.loadContentsByNum(eachPage)
                    } else {
                        val data = MutableLiveData<ContentsResource>()
                        executors.diskIO().execute { data.postValue(ContentsResource(state, null)) }
                        return data
                    }
                }

                override fun createCall() = service.getPublic(page, eachPage)
            }.asLiveData()
        } else {
            return object : NetworkDirectiveResource<ContentsResource, PublishContentResponse>(executors) {
                override fun convertToResource(data: PublishContentResponse) = data.toContentsResource()
                override fun createCall() = service.getPublic(page, eachPage)
            }.asLiveData()
        }
    }
}
