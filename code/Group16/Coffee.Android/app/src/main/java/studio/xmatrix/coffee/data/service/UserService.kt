package studio.xmatrix.coffee.data.service

import android.arch.lifecycle.LiveData
import retrofit2.http.Body
import retrofit2.http.POST
import studio.xmatrix.coffee.data.common.network.ApiResponse
import javax.inject.Singleton

@Singleton
interface UserService {

    companion object {
        const val BASE_URL = "https://coffee.zhenly.cn/api/"
    }

    data class LoginReq(val name: String, val password: String)

    @POST("user/login/pass")
    fun loginByPassword(@Body body: LoginReq): LiveData<ApiResponse<CommonRes>>
}
