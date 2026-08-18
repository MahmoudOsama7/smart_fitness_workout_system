package com.example.di.modules

import com.example.data.apiService.WorkoutSyncApi
import com.example.network.BuildConfig
import com.example.network.MockSyncBackendInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        mockInterceptor: MockSyncBackendInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)

        if (BuildConfig.IS_OFFLINE_MODE) {
            builder.addInterceptor(mockInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideWorkoutSyncApi(
        okHttpClient: OkHttpClient
    ): WorkoutSyncApi {
        val baseUrl =  BuildConfig.BASE_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WorkoutSyncApi::class.java)
    }
}