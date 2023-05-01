package com.appyael.rickandmortyapp.data.di

import com.appyael.rickandmortyapp.data.Paths
import com.appyael.rickandmortyapp.data.RickAndMortyApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.logging.Level
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Paths.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRickAndMortyApi(retrofit: Retrofit): RickAndMortyApi{
        return retrofit.create(RickAndMortyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}