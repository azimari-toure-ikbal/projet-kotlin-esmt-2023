package com.esmt.projet.victodo.di

import android.app.Application
import androidx.room.Room
import com.esmt.projet.victodo.feature_list.data.data_source.MockupDatabase
import com.esmt.projet.victodo.feature_list.data.repository.MockupRepositoryImpl
import com.esmt.projet.victodo.feature_list.domain.repository.MockupRepository
import com.esmt.projet.victodo.feature_list.domain.use_case.MockupGetAllUseCase
import com.esmt.projet.victodo.feature_list.domain.use_case.MockupUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VictoAppModule {

    @Provides
    @Singleton
    fun provideMockupDatabase(app: Application): MockupDatabase {
        return Room.databaseBuilder(
            app,
            MockupDatabase::class.java,
            MockupDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMockupRepository(db: MockupDatabase): MockupRepository {
        return MockupRepositoryImpl(db.mockupDao)
    }

    @Provides
    @Singleton
    fun provideMockupUseCases(repository: MockupRepository): MockupUseCases {
        return MockupUseCases(
            getAll = MockupGetAllUseCase(repository)
        )
    }
}