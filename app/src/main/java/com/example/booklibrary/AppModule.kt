package com.example.booklibrary

import com.example.booklibrary.data.network.BookApiService
import com.example.booklibrary.data.repository.BookRepositoryImpl
import com.example.booklibrary.domain.usecase.ListUseCase
import com.example.booklibrary.domain.usecase.ListUseCaseImpl
import com.example.booklibrary.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://openlibrary.org/people/mekBot/books/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApiService(retrofit: Retrofit): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookRepository(apiService: BookApiService): BookRepository {
        return BookRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideListUseCase(repository: BookRepository): ListUseCase {
        return ListUseCaseImpl(repository)
    }
}