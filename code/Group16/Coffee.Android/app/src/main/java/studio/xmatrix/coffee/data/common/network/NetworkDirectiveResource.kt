package studio.xmatrix.coffee.data.common.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

abstract class NetworkDirectiveResource<Type>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<Type>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<Type>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.mainThread().execute {
                        val liveData = MutableLiveData<Type>()
                        liveData.value = processResponse(response)
                        result.addSource(liveData) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        val liveData = MutableLiveData<Type>()
                        liveData.value = null
                        result.addSource(liveData) { newData ->
                            setValue(Resource.success(newData))
                        }

                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    val liveData = MutableLiveData<Type>()
                    liveData.value = null
                    result.addSource(liveData) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<Type>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<Type>) = response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<Type>>
}
