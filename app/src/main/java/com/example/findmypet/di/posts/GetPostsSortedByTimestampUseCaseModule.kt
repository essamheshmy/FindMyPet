package com.example.findmypet.di.posts

import com.example.findmypet.domain.repository.PostRepository
import com.example.findmypet.domain.usecase.firebaseUseCase.posts.GetPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetPostsSortedByTimestampUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetPostsSortedByTimestampUseCase(postRepository: PostRepository): GetPostsUseCase {
        return GetPostsUseCase(postRepository)
    }
}
