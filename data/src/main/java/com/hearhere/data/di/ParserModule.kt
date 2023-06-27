package com.hearhere.data.di

import com.hearhere.data.data.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ParserModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class parsing

    @Provides
    fun provideBaseUrl() = "https://www.maniadb.com/" // 추후 추가

    @Singleton
    @Provides
    @parsing
    fun provideXMLInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @parsing
    fun provideParsingService(@parsing retrofit: Retrofit) = retrofit.create(MusicParsingService::class.java)

    @Singleton
    @Provides
    fun provideParsingHelper(parsingHelper: ParsingHelperImpl): ParsingHelper = parsingHelper

    @Singleton
    @Provides
    fun provideSearchParser(parser: SearchArtistParser) = parser
}
