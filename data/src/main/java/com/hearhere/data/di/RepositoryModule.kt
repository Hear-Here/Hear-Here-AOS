package com.hearhere.data.di


import com.hearhere.data.data.network.SearchArtistParser
import com.hearhere.data.repositoryImpl.AuthRepositoryImpl
import com.hearhere.data.repositoryImpl.PreferenceRepositoryImpl
import com.hearhere.data.repositoryImpl.SearchMusicRepositoryImpl
import com.hearhere.data.repositoryImpl.TestRepositoryImpl
import com.hearhere.domain.repository.AuthRepository
import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.domain.repository.SearchMusicRepository
import com.hearhere.domain.usecase.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindTestRepository(repository: TestRepositoryImpl): TestRepository

    @Binds
    abstract fun bindMusicSearchRepository(repository: SearchMusicRepositoryImpl): SearchMusicRepository


    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindPrefRepository(repository: PreferenceRepositoryImpl): PreferenceRepository
}