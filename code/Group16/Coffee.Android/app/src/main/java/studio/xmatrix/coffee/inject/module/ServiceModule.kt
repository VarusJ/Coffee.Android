package studio.xmatrix.coffee.inject.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import studio.xmatrix.coffee.data.common.adapter.LiveDataCallAdapterFactory
import studio.xmatrix.coffee.data.service.UserService
import javax.inject.Singleton

@Module
class ServiceModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(UserService.BASE_URL)
            .build()
        return retrofit.create(UserService::class.java)
    }
}
