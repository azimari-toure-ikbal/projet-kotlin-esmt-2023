package com.esmt.projet.victodo.di

//import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
//import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import android.app.Application
import android.content.Context
import android.util.Log
import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
import com.esmt.projet.victodo.feature_task.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  VictoAppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        Log.i("infoDataR", "build de la db")
        return TaskDatabase.getInstance(app)
//        return Room.databaseBuilder(
//            app,
//            TaskDatabase::class.java,
//            TaskDatabase.DATABASE_NAME
//        ).build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}