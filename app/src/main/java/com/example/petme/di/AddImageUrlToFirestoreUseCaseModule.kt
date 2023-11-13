package com.example.petme.di

import com.example.petme.domain.repository.EditProfileRepository
import com.example.petme.domain.usecase.firebaseUseCase.profile.AddImageUrlToFirestoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AddImageUrlToFirestoreUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideAddImageUrlToFirestoreUseCase(editProfileRepository: EditProfileRepository): AddImageUrlToFirestoreUseCase {
        return AddImageUrlToFirestoreUseCase(editProfileRepository)
    }
}