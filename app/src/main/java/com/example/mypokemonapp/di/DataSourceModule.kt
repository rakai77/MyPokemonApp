package com.example.mypokemonapp.di

import com.example.mypokemonapp.data.remote.RemoteDataSource
import com.example.mypokemonapp.data.remote.RemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp) : RemoteDataSource
}