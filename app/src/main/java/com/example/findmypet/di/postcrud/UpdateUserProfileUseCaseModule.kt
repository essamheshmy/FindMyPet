package com.example.findmypet.di.postcrud

import com.example.findmypet.domain.repository.EditProfileRepository
import com.example.findmypet.domain.usecase.firebaseUseCase.profile.UpdateUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UpdateUserProfileUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideUpdateUserProfileUseCase(editProfileRepository: EditProfileRepository): UpdateUserProfileUseCase {
        return UpdateUserProfileUseCase(editProfileRepository)
    }
}
