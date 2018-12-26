package studio.xmatrix.coffee.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserService {

    companion object {
        const val BASE_URL = "https://coffee.zhenly.cn/api/"
    }

    @POST("user/login/pass")
    fun loginByPassword(): Call<ResponseBody>
}
