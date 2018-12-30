package studio.xmatrix.coffee.inject.module

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import studio.xmatrix.coffee.App
import studio.xmatrix.coffee.data.common.adapter.LiveDataCallAdapterFactory
import studio.xmatrix.coffee.data.service.ContentService
import studio.xmatrix.coffee.data.service.UserService
import javax.inject.Singleton


@Module
class ServiceModule {

    @Singleton
    @Provides
    fun provideUserService(app: App): UserService {
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(app))
        val okHttpClient = OkHttpClient.Builder().cookieJar(cookieJar).build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(UserService.BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideContentService(app: App): ContentService {
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(app))
        val okHttpClient = OkHttpClient.Builder().cookieJar(cookieJar).build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ContentService.BASE_URL)
            .client(okHttpClient)
            .build()
        return retrofit.create(ContentService::class.java)
    }
}
