package studio.xmatrix.coffee.data.repository

import android.arch.lifecycle.LiveData
import android.preference.PreferenceManager
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.network.*
import studio.xmatrix.coffee.data.model.User
import studio.xmatrix.coffee.data.service.UserDatabase
import studio.xmatrix.coffee.data.service.UserService
import studio.xmatrix.coffee.data.service.response.CommonResponse
import studio.xmatrix.coffee.data.service.response.UserInfoResponse
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val app: App,
    private val executors: AppExecutors,
    private val service: UserService,
    private val database: UserDatabase
) {

    fun login(name: String, password: String): LiveData<Resource<CommonResponse>> {
        return object : NetworkDirectiveResource<CommonResponse, CommonResponse>(executors) {
            override fun createCall(): LiveData<ApiResponse<CommonResponse>> {
                val bytes = password.toByteArray()
                val md = MessageDigest.getInstance("SHA-512")
                val digest = md.digest(bytes)
                val hash = digest.fold("") { str, it -> str + "%02x".format(it) }
                return service.loginByPassword(UserService.LoginRequestBody(name, hash))
            }

            override fun convertToResource(data: CommonResponse) = data
        }.asLiveData()
    }

    fun register(name: String, email: String, password: String): LiveData<Resource<CommonResponse>> {
        return object : NetworkDirectiveResource<CommonResponse, CommonResponse>(executors) {
            override fun createCall(): LiveData<ApiResponse<CommonResponse>> {
                val bytes = password.toByteArray()
                val md = MessageDigest.getInstance("SHA-512")
                val digest = md.digest(bytes)
                val hash = digest.fold("") { str, it -> str + "%02x".format(it) }
                return service.register(UserService.RegisterRequestBody(name, email, hash))
            }

            override fun convertToResource(data: CommonResponse) = data
        }.asLiveData()
    }

    fun getEmailValidCode(): LiveData<Resource<CommonResponse>> {
        return object : NetworkDirectiveResource<CommonResponse, CommonResponse>(executors) {
            override fun createCall() = service.getEmailValidCode()
            override fun convertToResource(data: CommonResponse) = data
        }.asLiveData()
    }

    fun validEmail(vcode: String): LiveData<Resource<CommonResponse>> {
        return object : NetworkDirectiveResource<CommonResponse, CommonResponse>(executors) {
            override fun createCall() = service.validEmail(UserService.EmailValidRequestBody(vcode))
            override fun convertToResource(data: CommonResponse) = data
        }.asLiveData()
    }

    fun getInfo(): LiveData<Resource<User>> {
        val pref = PreferenceManager.getDefaultSharedPreferences(app)
        return object : NetworkBoundResource<User, UserInfoResponse>(executors) {
            override fun saveCallResult(item: UserInfoResponse) {
                val editor = pref.edit()
                editor.putString("id", item.id)
                editor.apply()
                database.saveInfo(item.toUser())
            }

            override fun shouldFetch(data: User?) = true
            override fun loadFromDb() = database.loadInfoById(pref.getString("id", null))
            override fun createCall() = service.getInfoById("self")
        }.asLiveData()
    }

    fun getInfoById(id: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, UserInfoResponse>(executors) {
            override fun saveCallResult(item: UserInfoResponse) = database.saveInfo(item.toUser())
            override fun shouldFetch(data: User?) = true
            override fun loadFromDb() = database.loadInfoById(id)
            override fun createCall() = service.getInfoById(id)
        }.asLiveData()
    }
}
