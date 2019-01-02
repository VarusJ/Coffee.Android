package studio.xmatrix.coffee.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import studio.xmatrix.coffee.data.common.network.*
import studio.xmatrix.coffee.data.model.Content
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
import java.io.File
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
                        database.savePublicContents(item.toContentsResource().resource)
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

    fun addAlbum(
        title: String,
        detail: String,
        tags: List<String>,
        public: Boolean,
        images: List<File>
    ): LiveData<Resource<CommonResource>> {
        return object : NetworkDirectiveResource<CommonResource, CommonResource>(executors) {
            override fun convertToResource(data: CommonResource) = data

            override fun createCall(): LiveData<ApiResponse<CommonResource>> {
                val list = ArrayList<MultipartBody.Part>()
                for ((i, image) in images.withIndex()) {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), image)
                    val part = MultipartBody.Part.createFormData("file" + i.toString(), image.name, body)
                    list.add(part)
                    // val thumbBody = RequestBody.create(MediaType.parse("multipart/form-data"), )
                }
                TODO()
            }
        }.asLiveData()
    }

    fun getAlbums(): LiveData<Resource<ContentsResource>> {
        var state = CommonResource.StatusSuccess
        return object : NetworkBoundResource<ContentsResource, ContentsResponse>(executors) {
            override fun saveCallResult(item: ContentsResponse) {
                state = item.state
                if (item.state == CommonResource.StatusSuccess) {
                    database.saveContents(item.toContentsResource().resource)
                }
            }

            override fun shouldFetch(data: ContentsResource?) = true

            override fun loadFromDb(): LiveData<ContentsResource> {
                return if (state == CommonResource.StatusSuccess) {
                    database.loadContentsByType(Content.TypeAlbum)
                } else {
                    val data = MutableLiveData<ContentsResource>()
                    executors.diskIO().execute { data.postValue(ContentsResource(state, null)) }
                    data
                }
            }

            override fun createCall() = service.getAlbumsByUserId("self")
        }.asLiveData()
    }

    fun getAlbumsByUserId(userId: String): LiveData<Resource<ContentsResource>> {
        return object : NetworkDirectiveResource<ContentsResource, ContentsResponse>(executors) {
            override fun convertToResource(data: ContentsResponse) = data.toContentsResource()
            override fun createCall() = service.getAlbumsByUserId(userId)
        }.asLiveData()
    }

    fun getTexts(): LiveData<Resource<ContentsResource>> {
        var state = CommonResource.StatusSuccess
        return object : NetworkBoundResource<ContentsResource, ContentsResponse>(executors) {
            override fun saveCallResult(item: ContentsResponse) {
                state = item.state
                if (item.state == CommonResource.StatusSuccess) {
                    database.saveContents(item.toContentsResource().resource)
                }
            }

            override fun shouldFetch(data: ContentsResource?) = true

            override fun loadFromDb(): LiveData<ContentsResource> {
                return if (state == CommonResource.StatusSuccess) {
                    database.loadContentsByType(Content.TypeText)
                } else {
                    val data = MutableLiveData<ContentsResource>()
                    executors.diskIO().execute { data.postValue(ContentsResource(state, null)) }
                    data
                }
            }

            override fun createCall() = service.getTextsByUserId("self")
        }.asLiveData()
    }

    fun getTextsByUserId(userId: String): LiveData<Resource<ContentsResource>> {
        return object : NetworkDirectiveResource<ContentsResource, ContentsResponse>(executors) {
            override fun convertToResource(data: ContentsResponse) = data.toContentsResource()
            override fun createCall() = service.getTextsByUserId(userId)
        }.asLiveData()
    }

    fun addText(
        title: String,
        detail: String,
        tags: List<String>,
        public: Boolean
    ): LiveData<Resource<CommonResource>> {
        return object : NetworkDirectiveResource<CommonResource, CommonResource>(executors) {
            override fun convertToResource(data: CommonResource) = data
            override fun createCall() = service.addText(ContentService.ContentRequestBody(title, detail, tags, public))
        }.asLiveData()
    }

    fun updateContentById(
        id: String,
        title: String,
        detail: String,
        tags: List<String>,
        public: Boolean
    ): LiveData<Resource<CommonResource>> {
        return object : NetworkDirectiveResource<CommonResource, CommonResource>(executors) {
            override fun convertToResource(data: CommonResource) = data
            override fun createCall() =
                service.updateById(id, ContentService.ContentRequestBody(title, detail, tags, public))
        }.asLiveData()
    }

    fun getThumbByFilename(filename: String): LiveData<Resource<Bitmap>> {
        return object : NetworkBoundResource<Bitmap, Bitmap>(executors) {
            override fun saveCallResult(item: Bitmap) = imageDatabase.saveById(filename, item)
            override fun shouldFetch(data: Bitmap?) = data == null
            override fun loadFromDb() = imageDatabase.loadById(filename)
            override fun createCall() = imageService.getByFilename(filename)
        }.asLiveData()
    }

    fun getImageByIdAndPath(id: String, path: String): LiveData<Resource<Bitmap>> {
        val newPath = path.replace('/', '|')
        return object : NetworkBoundResource<Bitmap, Bitmap>(executors) {
            override fun saveCallResult(item: Bitmap) = imageDatabase.saveById(id, item)
            override fun shouldFetch(data: Bitmap?) = data == null
            override fun loadFromDb() = imageDatabase.loadById(id)
            override fun createCall() = service.getImageByIdAndPath(id, newPath)
        }.asLiveData()
    }
}
