package com.example.recipes.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.recipes.data.repository.ProfileImageRepositoryImpl
import com.example.recipes.domain.repository.ProfileImageRepository

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideProfileImageRepository(): ProfileImageRepository = ProfileImageRepositoryImpl(
        storage = Firebase.storage,
        db = Firebase.firestore
    )
}