package com.hearhere.data.di

import com.hearhere.data.BuildConfig
import com.hearhere.data.data.network.APIInterceptor
import com.hearhere.data.data.network.ApiHelper
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.data.data.network.HearHereApiService
import com.hearhere.data.repositoryImpl.PreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class api



    private val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: APIInterceptor) = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @api
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()



    @Singleton
    @Provides
    @api
    fun provideApiService( @api retrofit: Retrofit) = retrofit.create(HearHereApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


    @Provides
    @Singleton
    fun provideAuthIntercepter(repository : PreferenceRepositoryImpl) = APIInterceptor(repository)
}