package studio.xmatrix.coffee.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import studio.xmatrix.coffee.data.common.network.*
import studio.xmatrix.coffee.data.service.ContentDatabase
import studio.xmatrix.coffee.data.service.ContentService
import studio.xmatrix.coffee.data.service.ImageDatabase
import studio.xmatrix.coffee.data.service.ImageService
import studio.xmatrix.coffee.data.service.resource.CommonResource
import studio.xmatrix.coffee.data.service.resource.ContentResource
import studio.xmatrix.coffee.data.service.resource.ContentsResource
import studio.xmatrix.coffee.data.service.response.ContentResponse
import studio.xmatrix.coffee.data.service.response.ContentsResponse
import studio.xmatrix.coffee.data.service.response.PublishContentsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val executors: AppExecutors,
    private val service: ContentService,
    private val database: ContentDatabase,
    private val imageService: ImageService,
    private val imageDatabase: ImageDatabase
) {

    fun deleteContentById(id: String): LiveData<Resource<CommonResource>> {
        return object : NetworkDirectiveResource<CommonResource, CommonResource>(executors) {
            override fun convertToResource(data: CommonResource) = data
            override fun createCall() = service.deleteById(id)
        }.asLiveData()
    }

    fun getContentById(id: String): LiveData<Resource<ContentResource>> {
        return object : NetworkDirectiveResource<ContentResource, ContentResponse>(executors) {
            override fun convertToResource(data: ContentResponse) = data.toContentResource()
            override fun createCall() = service.getById(id)
        }.asLiveData()
    }

    fun getPublicContentsByPage(page: Int, eachPage: Int): LiveData<Resource<ContentsResource>> {
        if (page == 1) {
            var state = CommonResource.StatusSuccess
            return object : NetworkBoundResource<ContentsResource, PublishContentsResponse>(executors) {
                override fun saveCallResult(item: PublishContentsResponse) {
                    state = item.state
                    if (item.state == CommonResource.StatusSuccess) {
                        database.saveContents(item.toContentsResource().resource)
                    }
                }

                override fun shouldFetch(data: ContentsResource?) = true

                override fun loadFromDb(): LiveData<ContentsResource> {
                    return if (state == CommonResource.StatusSuccess) {
                        database.loadPublicContentsByNum(eachPage)
                    } else {
                        val data = MutableLiveData<ContentsResource>()
                        executors.diskIO().execute { data.postValue(ContentsResource(state, null)) }
                        data
                    }
                }

                override fun createCall() = service.getPublicListByPage(page, eachPage)
            }.asLiveData()
        } else {
            return object : NetworkDirectiveResource<ContentsResource, PublishContentsResponse>(executors) {
                override fun convertToResource(data: PublishContentsResponse) = data.toContentsResource()
                override fun createCall() = service.getPublicListByPage(page, eachPage)
            }.asLiveData()
        }
    }

    fun addAlbum(): LiveData<Resource<CommonResource>> {
        TODO("not implemented")
    }

    fun getAlbumsByUserId(id: String): LiveData<Resource<ContentsResource>> {
        var state = CommonResource.StatusSuccess
        return object : NetworkBoundResource<ContentsResource, ContentsResponse>(executors) {
            override fun saveCallResult(item: ContentsResponse) {
                state = item.state
                if (item.state == CommonResource.StatusSuccess) {
                }
            }

            override fun shouldFetch(data: ContentsResource?) = true

            override fun loadFromDb(): LiveData<ContentsResource> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): LiveData<ApiResponse<ContentsResponse>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }.asLiveData()
    }

    fun getTextsByUserId() = {}
    fun addText() = {}
    fun updateContentById() = {}

    fun getThumbByFilename(filename: String): LiveData<Resource<Bitmap>> {
        return object : NetworkBoundResource<Bitmap, Bitmap>(executors) {
            override fun saveCallResult(item: Bitmap) = imageDatabase.saveById(filename, item)
            override fun shouldFetch(data: Bitmap?) = data == null
            override fun loadFromDb() = imageDatabase.loadById(filename)
            override fun createCall() = imageService.getByFilename(filename)
        }.asLiveData()
    }

    fun getImageByIdAndPath(id: String, path: String): LiveData<Resource<Bitmap>> {
        return object : NetworkBoundResource<Bitmap, Bitmap>(executors) {
            override fun saveCallResult(item: Bitmap) = imageDatabase.saveById(id, item)
            override fun shouldFetch(data: Bitmap?) = data == null
            override fun loadFromDb() = imageDatabase.loadById(id)
            override fun createCall() = service.getImageByIdAndPath(id, path)
        }.asLiveData()
    }
}
