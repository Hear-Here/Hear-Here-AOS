package com.hearhere.data.di


import com.hearhere.data.repositoryImpl.TestRepositoryImpl
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
}