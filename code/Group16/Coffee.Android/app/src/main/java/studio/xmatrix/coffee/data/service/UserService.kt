package studio.xmatrix.coffee.data.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.network.ApiResponse
import studio.xmatrix.coffee.data.common.network.AppExecutors
import studio.xmatrix.coffee.data.model.User
import studio.xmatrix.coffee.data.model.User_
import studio.xmatrix.coffee.data.service.response.CommonResponse
import studio.xmatrix.coffee.data.service.response.UserInfoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface UserService {
    companion object {
        const val BASE_URL = "https://coffee.zhenly.cn/api/user/"
    }

    @POST("login/pass")
    fun loginByPassword(@Body body: LoginRequestBody): LiveData<ApiResponse<CommonResponse>>

    @POST("register")
    fun register(@Body body: RegisterRequestBody): LiveData<ApiResponse<CommonResponse>>

    @POST("email")
    fun getEmailValidCode(): LiveData<ApiResponse<CommonResponse>>

    @POST("valid")
    fun validEmail(@Body body: EmailValidRequestBody): LiveData<ApiResponse<CommonResponse>>

    // 当id为self时获取自身数据
    @GET("info/{id}")
    fun getInfoById(@Path("id") id: String): LiveData<ApiResponse<UserInfoResponse>>

    data class LoginRequestBody(val name: String, val password: String)
    data class RegisterRequestBody(val name: String, val email: String, val password: String)
    data class EmailValidRequestBody(val vCode: String)
}

@Singleton
class UserDatabase @Inject constructor(app: App, private val executors: AppExecutors) {

    private val box: Box<User> = app.boxStore.boxFor()

    fun loadInfoById(id: String?): LiveData<User> {
        val data = MutableLiveData<User>()
        executors.diskIO().execute {
            data.postValue(box.query { equal(User_.id, id ?: "") }.findFirst())
        }
        return data
    }

    fun saveInfo(user: User) {
        box.query().equal(User_.id, user.id).build().remove()
        box.put(user)
    }
}
