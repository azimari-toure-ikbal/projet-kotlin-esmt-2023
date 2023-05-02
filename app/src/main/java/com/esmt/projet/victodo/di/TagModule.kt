package com.esmt.projet.victodo.di

import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_tag.data.repository.TagRepositoryImpl
import com.esmt.projet.victodo.feature_tag.domain.repository.TagRepository
import com.esmt.projet.victodo.feature_tag.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TagModule {

    @Provides
    @Singleton
    fun provideTagRepository(db:TaskDatabase): TagRepository{
        return TagRepositoryImpl(db.tagDao)
    }
    @Provides
    @Singleton
    fun provideTagUseCases(repository: TagRepository): TagUseCases {
        return TagUseCases(
            getAllTagUseCase = GetAllTagUseCase(repository),
            getTagUseCase =  GetTagUseCase(repository),
            deleteTagUseCase = DeleteTagUseCase(repository),
            addTagUseCase = AddTagUseCase(repository)
        )
    }
}