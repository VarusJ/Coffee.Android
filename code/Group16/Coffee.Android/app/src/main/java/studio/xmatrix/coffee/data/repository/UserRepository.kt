package studio.xmatrix.coffee.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import studio.xmatrix.coffee.data.common.network.ApiResponse
import studio.xmatrix.coffee.data.common.network.AppExecutors
import studio.xmatrix.coffee.data.common.network.NetworkDirectiveResource
import studio.xmatrix.coffee.data.common.network.Resource
import studio.xmatrix.coffee.data.service.CommonRes
import studio.xmatrix.coffee.data.service.UserService
import timber.log.Timber
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userService: UserService
) {

    fun login(name: String, password: String): LiveData<Resource<CommonRes>> {
        var hash: String
        return object : NetworkDirectiveResource<CommonRes>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<CommonRes>> {
                val bytes = password.toByteArray()
                val md = MessageDigest.getInstance("SHA-512")
                val digest = md.digest(bytes)
                hash = digest.fold("") { str, it -> str + "%02x".format(it) }
                return userService.loginByPassword(UserService.LoginReq(name, hash))
            }
        }.asLiveData()
    }
}
